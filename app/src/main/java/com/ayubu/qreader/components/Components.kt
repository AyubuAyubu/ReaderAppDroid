package com.ayubu.qreader.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.ayubu.qreader.R
import com.ayubu.qreader.model.MBook
import com.ayubu.qreader.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ReaderLogo(modifier: Modifier = Modifier) {
    Text(
        text = "D. Reader",
        modifier=modifier.padding(bottom = 16.dp),
        style = MaterialTheme.typography.bodyMedium,
        color = Color.Red.copy(alpha = 0.5f)
    )
}


@Composable
fun EmailInput(modifier:Modifier=Modifier,
               emailState: MutableState<String>,
               labelId:String ="Email",
               enabled:Boolean =true,
               imeAction: ImeAction = ImeAction.Next,
               onAction: KeyboardActions = KeyboardActions.Default
){
    InputField(
        modifier=modifier,
        valueState = emailState,
        labelId =labelId,
        enabled=enabled,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    modifier:Modifier=Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine:Boolean =true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value=it
        },
        label = { Text(text = labelId)},
        singleLine=isSingleLine,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color=MaterialTheme.colorScheme.onBackground),
        modifier= modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled=enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType=keyboardType,
            imeAction = imeAction
        )
    )
}

@Composable
fun TitleSection(modifier: Modifier=Modifier,label:String){
    Surface(modifier=modifier.padding(start=5.dp,top=1.dp)) {
        Column {
            Text(text = label,
                fontSize = 19.sp,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Left
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderAppBar(
    title:String,
    icon: ImageVector?=null,
    showProfile:Boolean=true,
    navController: NavController,
    onBackArrowClicked:() -> Unit={}
){
    //background color and elevation
    TopAppBar(title={
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (showProfile){
                Image(
                    imageVector =Icons.Default.Favorite,
                    contentDescription = "Logo icon",
                    modifier= Modifier
                        .clip(
                            RoundedCornerShape(12.dp)
                        )
                        .scale(0.9f)
                )
                if (icon != null){
                    Icon(imageVector =icon,
                        contentDescription ="arrow back",
                        tint = Color.Red.copy(alpha = 0.7f),
                        modifier = Modifier.clickable {
                            onBackArrowClicked.invoke()
                        })
                }
                Spacer(modifier =Modifier.width(40.dp))
                Text(text = title,
                    color = Color.Red.copy(alpha = 0.7f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
            }
        }
    },
        actions = {
            IconButton(onClick = {
                FirebaseAuth.getInstance().signOut().run{
                    navController.navigate(ReaderScreens.LoginScreen.name)
                }
            }) {
                if (showProfile) Row() {
                    Icon(imageVector = Icons.Filled.Logout,
                        contentDescription ="Logout",
                        tint = Color.Green  .copy(0.4f)
                    )
                }else Box{}

            }
        },
        colors = TopAppBarDefaults.topAppBarColors())

}

@Composable
fun FABContent(onTap: () -> Unit) {
    FloatingActionButton(onClick = { onTap()},
        shape = RoundedCornerShape(50.dp),
        containerColor = Color(0xFF92CBDF)
    ){
        Icon(imageVector =Icons.Default.Add,
            contentDescription ="Add a Book",
            tint = Color.White
        )
    }
}

@Composable
fun BookRating(score: Double=4.5) {
    Surface(
        modifier = Modifier
            .height(70.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(56.dp),
        shadowElevation = 6.dp,
        color = Color.White
    ) {
        Column(
            modifier=Modifier.padding(4.dp)
        ) {
            Icon(imageVector = Icons.Filled.StarBorder,
                contentDescription ="Start",modifier=Modifier.padding(3.dp))
            Text(text = score.toString(), style =MaterialTheme.typography.bodySmall)

        }


    }
}

@Composable
fun RoundedButton(
    label:String="Reading",
    radius:Int=29,
    onPress:() ->Unit={}
){
    Surface(
        modifier=Modifier.clip(RoundedCornerShape(
            bottomEndPercent = radius,
            topStartPercent = radius
        )),
        color = Color(0xFF92CBDF)
    ) {
        Column(
            modifier= Modifier
                .width(90.dp)
                .heightIn(40.dp)
                .clickable {
                    onPress.invoke()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text =label,
                style= TextStyle(color=Color.White),
                fontSize = 15.sp
            )
        }
    }
}

@Preview
@Composable
fun ListCard(book: MBook = MBook(
    "ghf",
    "Droid Pwani",
    "Ayubu & Gitonga",
    "Welcome to droid",

    ), onPressDetails: (String) -> Unit ={}){
    //where we are displaying our app and get width of screen internally
    val context= LocalContext.current
    val resources=context.resources
    val displayMetrics =resources.displayMetrics
    //getting actual screen width
    val screenWidth=displayMetrics.widthPixels / displayMetrics.density

    val spacing=10.dp
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier
            .padding(16.dp)
            .height(242.dp)
            .width(202.dp)
            .clickable {
                onPressDetails.invoke(book.title.toString())
            }
    ) {


        Column(
            modifier=Modifier.width(screenWidth.dp-(spacing*2)),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Image(painter = rememberImagePainter(data = ""),
                    contentDescription ="Book Image",
                    modifier = Modifier
                        .height(140.dp)
                        .width(100.dp)
                        .padding(4.dp)
                )
                Spacer(modifier = Modifier.width(50.dp))

                Column(
                    modifier=Modifier.padding(top=35.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription="Fav icon",
                        modifier =Modifier.padding(bottom = 1.dp))
                    BookRating(score=3.5)
                }

            }
            Text(
                text = book.title.toString(),
                modifier = Modifier.padding(4.dp),
                fontWeight = FontWeight.Bold,
                maxLines=2,
                overflow= TextOverflow.Ellipsis
            )
            Text(text = book.authors.toString(),
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.bodySmall
            )

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                RoundedButton(label = "Reading", radius = 70)
            }

        }
    }
}

