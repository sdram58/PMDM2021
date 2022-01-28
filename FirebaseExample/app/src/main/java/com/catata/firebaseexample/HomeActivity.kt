package com.catata.firebaseexample

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.catata.firebaseexample.databinding.ActivityHomeBinding
import com.catata.firebaseexample.models.Users
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig


const val EMAIL ="EMAIL"
const val PROVIDER ="PROVIDER"

enum class ProviderType{
    BASIC, GOOGLE
}
class HomeActivity : AppCompatActivity() {
    //Create our type of data retrieving
    val usersListType: GenericTypeIndicator<MutableList<Users>> =
        object : GenericTypeIndicator<MutableList<Users>>() {}

    val TAG = "HomeActivity"
    private lateinit var binding:ActivityHomeBinding

    private val context:Context = this

    val db = Firebase.firestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setup
        val bundle = intent.extras
        val email = bundle?.getString(EMAIL).toString()
        val provider = bundle?.getString(PROVIDER).toString()
        setup(email, provider)

        //Data saving
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString(EMAIL,email)
        prefs.putString(PROVIDER,provider)
        prefs.apply()

        //Remote config
        setUpConfig()

        //RecyclerView
        setupRecyclerView()



    }




    private fun setUpConfig() {
        binding.btnForceError.visibility = View.GONE

        FirebaseRemoteConfig.getInstance().fetchAndActivate().addOnCompleteListener { task ->
            if(task.isSuccessful){
                val showErrorButton:Boolean = FirebaseRemoteConfig.getInstance().getBoolean("show_error_button")
                val errorButtonText:String = FirebaseRemoteConfig.getInstance().getString("error_button_text")

                if(showErrorButton){
                    binding.btnForceError.visibility = View.VISIBLE
                }
                binding.btnForceError.text = errorButtonText

            }
        }
    }

    private fun setup(email:String, provider:String) {
        title = "Home"

        binding.tvEmail.text = email
        binding.tvProvider.text = provider

        binding.btnLogout.setOnClickListener {
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            FirebaseAuth.getInstance().signOut()

            onBackPressed()
        }

        binding.btnForceError.setOnClickListener {

            //we send the user that is getting the error.
            FirebaseCrashlytics.getInstance().setUserId(email)

            //We can set customs key. In this case we can know which is the user provider when the app crashes
            FirebaseCrashlytics.getInstance().setCustomKey(PROVIDER,provider)

            FirebaseCrashlytics.getInstance().log("ForceError Button has been pressed")

            //Error forcing
            throw RuntimeException("Forcing Error")
        }

        binding.btnSave.setOnClickListener {
            //Collection name is "users" and add or update a document with email as primary key
            db.collection("users").document(email).set(
                hashMapOf(
                    "provider" to provider,
                    "address" to binding.etAddress.text.toString(),
                    "phone" to binding.etPhone.text.toString()
                )
            )
        }

        binding.btnGet.setOnClickListener {
            db.collection("users").document(email).get().addOnSuccessListener {
                binding.etAddress.setText(it.get("address") as String?)
                binding.etPhone.setText(it.get("phone") as String?)
            }
        }

        binding.btnDelete.setOnClickListener {
            db.collection("users").document(email).delete()
        }

    }


    private fun setupRecyclerView() {
        //Get the reference to RealTime database
        //val dbRealTime = Firebase.database()
        val dbRealTime = Firebase.database(" https://dam2example-default-rtdb.europe-west1.firebasedatabase.app")

        //Reference to our data
        val myRef = dbRealTime.getReference("users")

        //Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.



               // val userList = dataSnapshot.getValue(usersListType)
               // (binding.listItem.adapter as MyAdapter).modifyData(userList?: mutableListOf<Users>())

               // Log.d(TAG, "Value is: $userList")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        myRef.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val user = snapshot.getValue(Users::class.java)
                user?.id = snapshot.key?:"-1"
                (binding.listItem.adapter as MyAdapter).addUser(user ?: Users())
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val user = snapshot.getValue(Users::class.java)
                user?.id = snapshot.key?:"-1"
                (binding.listItem.adapter as MyAdapter).updateUser(user ?: Users())
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val user = snapshot.getValue(Users::class.java)
                user?.id = snapshot.key?:"-1"
                (binding.listItem.adapter as MyAdapter).delUser(user ?: Users())
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        with(binding.listItem){
             val llm =   LinearLayoutManager(this@HomeActivity)
            //llm.reverseLayout = true
            layoutManager = llm
            adapter = MyAdapter(mutableListOf<Users>()){
                    users ->
                Toast.makeText(this@HomeActivity,"Clicked on ${users.email}",Toast.LENGTH_SHORT).show()
            }

        }

        binding.fabAdd.setOnClickListener {
            val user = Users("id", "newuser@gmail.com","BASIC","Home of new User", "999 99 99 99")
            myRef.push().setValue(user) //firebase creates the key
            //myRef.child("id").setValue(user) //We add our own path (key/name)
                .addOnSuccessListener(OnSuccessListener<Void?> {
                    //Writing ok
                })
                .addOnFailureListener(OnFailureListener {
                    //Writing KO
                })
        }
    }

    inner class MyAdapter(private val userList:MutableList<Users>, private val onClick:(Users)->Unit):RecyclerView.Adapter<MyAdapter.MyHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            return MyHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false))
        }

        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            val user = userList.get(position)
            holder.tvEmail.text = user.email
            holder.tvAddress.text = user.address
            holder.tvPhone.text = user.phone
            holder.itemView.setOnClickListener { onClick(user) }
        }

        override fun getItemCount(): Int {
            return userList.size
        }

        fun modifyData(list:MutableList<Users>){
            userList.clear()
            userList.addAll(list)
            notifyDataSetChanged()
        }

        fun modifyData(user:Users){
            userList.add(user)
            notifyItemInserted(userList.size - 1)
            //binding.listItem.scrollToPosition(userList.size)
        }

        fun addUser(user:Users){
            userList.add(user)
            notifyItemInserted(userList.size - 1)
            scrollToBottom(binding.listItem)
        }

        fun delUser(user:Users){
            val pos = userList.indexOf(user)
            userList.remove(user)
            notifyItemRemoved(pos)
        }

        fun updateUser(user:Users){
            val pos = userList.indexOf(user)
            userList[pos] = user
            notifyItemChanged(pos)
        }

        private fun scrollToBottom(recyclerView: RecyclerView) {
            // scroll to last item to get the view of last item
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
            val adapter = recyclerView.adapter
            val lastItemPosition = adapter!!.itemCount - 1
            layoutManager!!.scrollToPositionWithOffset(lastItemPosition, 0)
            recyclerView.postDelayed( { // then scroll to specific offset
                val target = layoutManager.findViewByPosition(lastItemPosition)
                if (target != null) {
                    val offset = recyclerView.measuredHeight - target.measuredHeight
                    layoutManager.scrollToPositionWithOffset(lastItemPosition, offset)
                }
            }, 1000)
        }

        inner class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val tvEmail: TextView = itemView.findViewById<TextView>(R.id.tvEmailItem)
            val tvAddress: TextView = itemView.findViewById<TextView>(R.id.tvAddressItem)
            val tvPhone: TextView = itemView.findViewById<TextView>(R.id.tvPhoneItem)


        }
    }
}