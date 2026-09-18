package com.example.listycity3

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.layout.fillMaxSize


@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
//    onEditCity: (City, City) -> Unit,
    modifier: Modifier = Modifier,
) {
    var newCityName by remember { mutableStateOf("") }
    var newProvinceName by remember { mutableStateOf("") }
    var showAddCityFields by remember { mutableStateOf(false) }
    var showEditButtons by remember {mutableStateOf(false)}

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
            ){
                /** button to make edit options appear*/
                FloatingActionButton(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        showEditButtons = !showEditButtons
                    }
                ) {Text("edit")}

                /** button to make add menu appear */
                FloatingActionButton(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        showAddCityFields = !showAddCityFields
                    }
            ) {Text("+")}


        }
        if (showAddCityFields) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(  //input field for new city name
                    value = newCityName,
                    onValueChange = { newCityName = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(  //input field for new city's provence
                    value = newProvinceName,
                    onValueChange = { newProvinceName = it },
                    label = { Text("Province") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                            onAddCity(
                                City(
                                    name = newCityName,
                                    province = newProvinceName
                                )
                            )
                            newCityName = ""
                            newProvinceName = ""
                            showAddCityFields = false
                        }  // end of if-notBlank body
                    }  // end of onClick body
                ) {  // end of button constructor
                    Text("Add City")
                }  // end of addCity button
            } // end of input field row
        }  /** end of hidden active fields */

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(cities) { index, city ->

                CityRow(city = city, showEditButtons)

                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun CityRow(city: City, showEditButtons: Boolean) {
    var editIsPressed by remember {  mutableStateOf(false)}
    var editedCityName by remember {mutableStateOf("")}
    var editedProvinceName by remember {mutableStateOf("")}


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )


    }
    if (showEditButtons) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            if (editIsPressed){
                OutlinedTextField(  //input field for new city name
                    value = editedCityName,
                    onValueChange = { editedCityName = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(  //input field for new city's provence
                    value = editedProvinceName,
                    onValueChange = { editedProvinceName = it },
                    label = { Text("Province") },
                    modifier = Modifier.weight(1f)
                )
            }

            Button(
                modifier = Modifier.padding(4.dp),

                onClick = {
                    if (editedCityName.isNotBlank() && editedProvinceName.isNotBlank()){

                        // edit city function call here
                        editedCityName = ""
                        editedProvinceName = ""
                        editIsPressed = false
                    }
                    else{
                        editIsPressed = !editIsPressed
                    }

                }
            ) {
                if (editIsPressed){
                    Text("!")
                }
                else{
                    Text(">")
                }

            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),
            onAddCity = {},
//            onEditCity = { city: City, city1: City -> }
        )
    }
}