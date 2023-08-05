package com.ayubu.qreader.screens.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ayubu.qreader.components.ReaderAppBar
import com.ayubu.qreader.navigation.ReaderScreens
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.ayubu.qreader.components.InputField
import com.ayubu.qreader.model.MBook

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(navController: NavController,
                 viewModel: BookSearchViewModel= hiltViewModel()
){
    Scaffold(topBar = {
        ReaderAppBar(
            title ="Search Books",
            icon= Icons.Default.ArrowBack,
            navController =navController,
            showProfile=false
            ){
            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }
    }) {
        Surface() {
            Column{
                SearchForm(
                    modifier= Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                viewModel
                    ){query ->
                    viewModel.searchBooks(query)
                }

                Spacer(modifier = Modifier.height(15.dp))
                BookList(navController,viewModel)
            }
        }
    }
}

@Composable
fun BookList(navController: NavController,viewModel: BookSearchViewModel) {
    val listOfBooks= listOf(
        MBook(id="fgh",title = "Android254",authors = "Harun",notes = null),
        MBook(id="fgi",title = "Droid Pwani",authors = "James",notes = null),
        MBook(id="fgj",title = "OnlyDevs",authors = "Jacob",notes = null)
    )
  LazyColumn(modifier=Modifier.fillMaxSize(),
             contentPadding = PaddingValues(16.dp)
      ){
            
     items(items = listOfBooks){ book ->
         BookRow(book,navController)
      
    }

  }
}

@Composable
fun BookRow(book: MBook, navController: NavController) {
  Card(
      modifier= Modifier
          .clickable { }
          .fillMaxWidth()
          .height(100.dp)
          .padding(3.dp),
      shape = RectangleShape,
      elevation = CardDefaults.cardElevation(7.dp)
  ) {
      Row(
          modifier=Modifier.padding(5.dp),
          verticalAlignment = Alignment.Top
      ) {
          val imageUrl=""
          Image(painter = rememberAsyncImagePainter(model = imageUrl),
              contentDescription ="book image", modifier = Modifier.width(80.dp).fillMaxSize().padding(end=4.dp))
          Column() {
              Text(text = book.title.toString(), overflow = TextOverflow.Ellipsis)
              Text(text = "Author:${book.authors}",
                  overflow = TextOverflow.Clip,
                  style = MaterialTheme.typography.bodyMedium
                  )
              ///Todo Add more field later

          }
      }
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    modifier:Modifier=Modifier,
    viewModel: BookSearchViewModel,
    loading:Boolean=false,
    hint:String="Search",
    onSearch:(String) ->Unit={}
){
  Column {
      val searchQueryState= rememberSaveable { mutableStateOf("") }
      val keyboardController=LocalSoftwareKeyboardController.current
      val valid=remember(searchQueryState.value){
          searchQueryState.value.trim().isNotEmpty()
      }

      InputField(
          valueState =searchQueryState,
          labelId ="Search",
          enabled =true,
          onAction = KeyboardActions{
              if (!valid) return@KeyboardActions
              onSearch(searchQueryState.value.trim())
              searchQueryState.value=""
              keyboardController?.hide()
          }
      )
  }
}