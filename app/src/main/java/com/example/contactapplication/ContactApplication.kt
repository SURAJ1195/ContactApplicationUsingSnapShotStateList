package com.example.contactapplication

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

data class Contact(val name: String, val phoneNumber: String)

/*@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactApp() {
    var searchText by remember { mutableStateOf("") }
    var contacts by remember { mutableStateOf(listOf(
        Contact("Alice", "1234567890"),
        Contact("Bob", "9876543210"),
        Contact("Charlie", "5555555555")
    )) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contacts") }
            )
        },
        content = {
            Column(
                modifier = Modifier.padding(start = 0.dp,top = it.calculateTopPadding(),end = 0.dp)
            ) {
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it
                            contacts = filterContacts(it)
                                    },
                    label = { "Search" },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            // Perform search functionality here
                            // For example:
                            contacts = filterContacts(searchText)
                        }
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                ContactList(contacts)
            }
        }
    )
}

@Composable
fun ContactList(contacts: List<Contact>) {
    LazyColumn {
        items(contacts) { contact ->
            ContactItem(contact = contact)
            Divider()
        }
    }
}

@Composable
fun ContactItem(contact: Contact) {
    // You can design the UI for displaying each contact here
    // For example:
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {

            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = contact.name, modifier = Modifier.weight(1f))
        Text(text = contact.phoneNumber)
        // Add edit and other functionalities as needed
    }
}*/


fun filterContacts(searchText: String): MutableList<Contact> {
    // Logic to filter contacts based on search text
    // You can modify this according to your search requirements
    return listOf(
        Contact("Alice", "1234567890"),
        Contact("Bob", "9876543210"),
        Contact("Charlie", "5555555555")
    ).filter { (it.name.contains(searchText, ignoreCase = true)) || (it.phoneNumber.contains(searchText,ignoreCase = true)) }.toMutableList()
}

@Composable
fun ContactAppPreview() {
    ContactApp()
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactApp() {
    var searchText by remember { mutableStateOf("") }
    val contacts = remember { SnapshotStateList<Contact>() }


    val contacts2 = remember { mutableListOf(
        Contact("Alice", "1234567890"),
        Contact("Bob", "9876543210"),
        Contact("Charlie", "5555555555")
    ) }
    LaunchedEffect(key1 = Unit){
        contacts.addAll(contacts2)
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contacts") }
            )
        },
        content = {
            Column(
                modifier = Modifier.padding(start = 0.dp,top = it.calculateTopPadding(),end = 0.dp)
            ) {
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it
                                    contacts.removeAll(contacts)
                        contacts2.forEach { contact->
                            if(contact.name.contains(it, ignoreCase = true)){
                                contacts.add(contact)
                            }
                        }
                                    },
                    label = { Text("Search") },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {

                        }
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))
                
               ContactList(contacts = contacts){c->
                   if (contacts.contains(c)){
                       contacts.remove(c)
                       contacts2.remove(c)

                   }

               }
            }
        }
    )
}

@Composable
fun ContactList(contacts: List<Contact>, onContactClicked: (Contact) -> Unit) {
    LazyColumn {
        items(contacts) { contact ->
            ContactItem(contact = contact, onContactClicked = onContactClicked)
            Divider()
        }
    }
}

@Composable
fun ContactItem(contact: Contact, onContactClicked: (Contact) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = contact.name, modifier = Modifier.weight(1f))
        Text(text = contact.phoneNumber)
        IconButton(onClick = { onContactClicked(contact) }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete Contact")
        }
    }
}
