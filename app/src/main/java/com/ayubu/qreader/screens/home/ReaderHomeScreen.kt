package com.ayubu.qreader.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.ayubu.qreader.components.BookRating
import com.ayubu.qreader.components.FABContent
import com.ayubu.qreader.components.ListCard
import com.ayubu.qreader.components.ReaderAppBar
import com.ayubu.qreader.components.RoundedButton
import com.ayubu.qreader.components.TitleSection
import com.ayubu.qreader.model.MBook
import com.ayubu.qreader.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth

@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(navController: NavController=NavController(LocalContext.current)){
   Scaffold(
       topBar = {
            ReaderAppBar(title = "D.Reader", navController =navController )
       },
       floatingActionButton = {
           FABContent{
                navController.navigate(ReaderScreens.SearchScreen.name)
           }
       }
   ){
      Surface(
          modifier = Modifier.fillMaxSize()
      ) {
            HomeContent(navController)
      }
   }
}


@Composable
fun HomeContent(navController: NavController){
   val listOfBooks= listOf(
       MBook(id="fgh",title = "Android254",authors = "Harun",notes = null),
       MBook(id="fgi",title = "Droid Pwani",authors = "James",notes = null),
       MBook(id="fgj",title = "OnlyDevs",authors = "Jacob",notes = null)
   )

    // ayubu@gmail.com
    val myEmail = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName=if(!myEmail.isNullOrEmpty()){
      myEmail.split("@").get(0)
  }else{
      "Not Available"
    }
   Column(Modifier.padding(2.dp),
       verticalArrangement = Arrangement.Top
   ) {
       Row(modifier=Modifier.align(alignment = Alignment.Start)) {
           TitleSection(label = "Your reading\n"+"book right now")
           Spacer(modifier = Modifier.fillMaxWidth(0.7f))

           Column {
               Icon(imageVector = Icons.Filled.AccountCircle,
                   contentDescription ="Profile",
                    modifier= Modifier
                        .clickable {
                            navController.navigate(ReaderScreens.ReaderStatsScreen.name)
                        }
                        .size(45.dp),
                   tint = MaterialTheme.colorScheme.onSecondaryContainer
                   )

               Text(text = currentUserName,
                   modifier = Modifier.padding(2.dp),
                   style = MaterialTheme.typography.bodyMedium,
                   color = Color.Red,
                   fontSize = 15.sp,
                   maxLines = 1,
                   overflow = TextOverflow.Clip)
               Divider()
           }
       }

   }
    ReadingRightNowArea(books = listOf() , navController =navController)
    TitleSection(label = "Reading List")
    BookListArea(listOfBooks= listOfBooks,navController=navController)
}

@Composable
fun BookListArea(listOfBooks: List<MBook>, navController: NavController) {
    HorizontalScrollableComponent(listOfBooks){
        Log.d("TAG","BookListArea:$it")
        //Todo onCard click navigate to details
    }
}

@Composable
fun HorizontalScrollableComponent(listOfBooks: List<MBook>,onCardPressed:(String) -> Unit) {
    val scrollState= rememberScrollState()
    Row(
        modifier= Modifier
            .fillMaxWidth()
            .heightIn(200.dp)
            .horizontalScroll(scrollState)
    ) {
        for (book in listOfBooks){
            ListCard(book){
                onCardPressed(it)
            }
        }
    }
}


@Composable
fun ReadingRightNowArea(books:List<MBook>,navController: NavController){
    ListCard()
}

