package com.example.aston_intensive5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener

class ListOfContactsFragment : Fragment(R.layout.fragment_list) {

    private val listOfContacts: MutableList<Contact> = ArrayList()
    private val textViewsList: MutableList<TextView> = ArrayList()

    private var haveSecondFragment = false

    companion object {
        private const val EXTRA_HAVE_SEC_FRAG = "EXHASEFR"
        const val REQUEST_KEY = "R_KEY"

        fun newListFragment(have: Boolean): ListOfContactsFragment {
            val fragment = ListOfContactsFragment()
            Bundle().let {
                it.putBoolean(EXTRA_HAVE_SEC_FRAG, have)
                fragment.arguments = it
            }
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addContacts()
        arguments?.getBoolean(EXTRA_HAVE_SEC_FRAG)?.let {
            haveSecondFragment = it
        }

        setFragmentResultListener(REQUEST_KEY) { _, bundle ->
            val contactData = DetailsFragment.getContactDataFromBundle(bundle)
            val position = DetailsFragment.getPosition(bundle)
            listOfContacts[position] = contactData
            setText(position)
            setListener(position)
        }
    }

    private fun setListener(position: Int) {
        textViewsList[position].setOnClickListener(
            ContactListener(listOfContacts[position], position)
        )
    }

    private fun setText(position: Int) {
        textViewsList[position].text = listOfContacts[position].toString()
    }

    private fun addContacts() {
        listOfContacts.add(Contact("Александр", "Александров", "+7-***-***-**-**"))
        listOfContacts.add(Contact("Иван", "Иванов", "+7-***-***-**-**"))
        listOfContacts.add(Contact("Петр", "Петров", "+7-***-***-**-**"))
        listOfContacts.add(Contact("Олег", "Орлов", "+7-***-***-**-**"))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        initViews(view)
        return view
    }

    private fun initViews(v: View) {
        val textview1: TextView = v.findViewById(R.id.text_1)
        val textview2: TextView = v.findViewById(R.id.text_2)
        val textview3: TextView = v.findViewById(R.id.text_3)
        val textview4: TextView = v.findViewById(R.id.text_4)

        if (textViewsList.isNotEmpty()) {
            textViewsList.clear()
        }

        textview1.let { textViewsList.add(it) }
        textview2.let { textViewsList.add(it) }
        textview3.let { textViewsList.add(it) }
        textview4.let { textViewsList.add(it) }
    }

    override fun onStart() {
        super.onStart()
        setTexts()
        initListeners()
    }

    private fun setTexts() {
        for (i in 0 until textViewsList.size) {
            setText(i)
        }
    }

    private fun initListeners() {
        for (i in 0 until textViewsList.size) {
            setListener(i)
        }
    }

    inner class ContactListener(
        private val contactData: Contact,
        private val position: Int
    ) :
        View.OnClickListener {
        override fun onClick(p0: View?) {
            val fragment = DetailsFragment.newDetailFragment(contactData, position)
            requireActivity().supportFragmentManager.beginTransaction().run {
                if (haveSecondFragment) {
                    replace(R.id.fragment_detail_container, fragment, "TAG")
                } else replace(R.id.fragment_container, fragment, "TAG")
                addToBackStack("TAG1")
                commit()
            }
        }
    }
}