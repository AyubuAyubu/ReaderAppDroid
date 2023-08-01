package com.ayubu.qreader.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ayubu.qreader.R
import com.ayubu.qreader.components.EmailInput
import com.ayubu.qreader.components.ReaderLogo
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ayubu.qreader.navigation.ReaderScreens

@Composable
fun ReaderLoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel=viewModel()
                      ){
    val showLoginForm = rememberSaveable {
       mutableStateOf(true)
    }
    Surface(modifier = Modifier.fillMaxSize()) {
      Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
      ) {
            ReaderLogo()

            if (showLoginForm.value) UserForm(loading = false,isCreateAccount = false){ email,password ->
                viewModel.signInWithEmailAndPassword(email,password){
                    navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                }
            }
            else {
                UserForm(loading = false,isCreateAccount = true){ email,password ->
                    viewModel.createUserWithEmailAndPassword(email,password){
                        navController.navigate(ReaderScreens.ReaderHomeScreen.name)

                    }
                }
            }
      }
        Spacer(modifier = Modifier.height(25.dp)) //15.dp
        Row(
            modifier=Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
           val text = if (showLoginForm.value) "Sign up" else "Login"
            Text(text = "New User?")
            Text(text, modifier = Modifier
                .clickable {
                    showLoginForm.value = !showLoginForm.value
                }
                .padding(5.dp),
                fontWeight = FontWeight.Bold,
                color=MaterialTheme.colorScheme.onSecondary
                )
        }
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun UserForm(
    loading:Boolean = false,
    isCreateAccount:Boolean=false,
    onDone: (String,String) -> Unit ={ email, password -> }
){
    //remember the value beyond configuration change,remain beyond rotation
    val email = rememberSaveable{ mutableStateOf("") }
    val password = rememberSaveable{ mutableStateOf("") }
    val passwordVisibility = rememberSaveable{ mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController =LocalSoftwareKeyboardController.current
    val valid =remember(email.value,password.value){
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    //Since there is input have scrollable is best idea
    val modifier= Modifier
        .height(250.dp)
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(rememberScrollState())

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        if(isCreateAccount){
            Text(text = stringResource(id = R.string.create_account),modifier = Modifier.padding(4.dp))
        }else{
            Text("")
        }
            EmailInput(
                emailState =email,
                enabled = !loading,
                onAction = KeyboardActions{
                passwordFocusRequest.requestFocus()
            },
            )
        PasswordInput(
            modifier=Modifier.focusRequester(passwordFocusRequest),
            passwordState=password,
            labelId="Password",
            enabled=!loading,
            passwordVisibility=passwordVisibility,
            onAction=KeyboardActions{
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(),password.value.trim())
            })
        SubmitButton(
            textId= if (isCreateAccount) "Create Account" else "Login",
            loading=loading,
            validInputs =valid
        ){
            onDone(email.value.trim(),password.value.trim())
            keyboardController?.hide()
        }
    }
}

@Composable
fun SubmitButton(textId: String,
                 loading: Boolean,
                 validInputs: Boolean,
                 onClick:() -> Unit
) {
   Button(onClick = onClick,
       modifier= Modifier
           .padding(3.dp)
           .fillMaxWidth(),
       enabled = !loading && validInputs,
       shape = CircleShape
   ) {
       if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
       else  Text(text = textId, modifier = Modifier.padding(5.dp))
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(
    modifier: Modifier,
    passwordState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    passwordVisibility: MutableState<Boolean>,
    imeAction: ImeAction=ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    val visualTransformation =if (passwordVisibility.value) VisualTransformation.None else
        PasswordVisualTransformation()

OutlinedTextField(
    value = passwordState.value ,
    onValueChange ={
         passwordState.value =it
    },
    label = { Text(text = labelId)},
    singleLine=true,
    textStyle = TextStyle(
        fontSize = 18.sp,
        color=MaterialTheme.colorScheme.onBackground),
    modifier= modifier
        .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
        .fillMaxWidth(),
    enabled=enabled,
    keyboardOptions = KeyboardOptions(
        keyboardType=KeyboardType.Password,
        imeAction = imeAction
    ),
    visualTransformation = visualTransformation,
    trailingIcon = {
        PasswordVisibility(passwordVisibility=passwordVisibility)
    },
    keyboardActions = onAction
    )

}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>){
  val visible = passwordVisibility.value
  IconButton(onClick = { passwordVisibility.value = !visible}) {
      Icons.Default.Close

  }
}



