package com.example.proj1

import android.app.Application
import android.content.Intent
import androidx.compose.runtime.livedata.observeAsState
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.activity.viewModels
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.proj1.api.Country
import com.example.proj1.api.DataFilms
import com.example.proj1.api.Film
import com.example.proj1.api.FilmViewModel
import com.example.proj1.api.Genre
import com.example.proj1.db.User

import com.example.proj1.db.UserViewModel
import com.example.proj1.ui.theme.Proj1Theme

class UserViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(application) as T
    }
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel:FilmViewModel by viewModels()
            val navController = rememberNavController()
            Proj1Theme {
                NavHost(
                    navController =navController,
                    startDestination = "screen1"
                ){
                    composable("screen1"){
                        Screen1(navController)
                    }
                    composable("screen2"){
                        Screen2(navController,viewModel)
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen1(navController: NavController)
{
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        val owner = LocalViewModelStoreOwner.current
        owner?.let {
            val mUserViewModel: UserViewModel = viewModel(
                it,
                "UserViewModel",
                UserViewModelFactory(LocalContext.current.applicationContext as Application)
            )
            var userMap = HashMap<String, String>()
            var login by remember { mutableStateOf("") }
            var password by rememberSaveable { mutableStateOf("") }
            val userList by mUserViewModel.readAlldata.observeAsState(initial = listOf())
            val usermap = userList.map { it.username to it.password }.toMap()
            Column(
                Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround) {
                Spacer(modifier = Modifier.height(30.dp))
                LoginHeader()
                //Spacer(modifier = Modifier.height(30.dp))
                val hasErrorlog = login.isEmpty()
                val hasErrorpas = password.isEmpty()
                Column( Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround){

                    OutlinedTextField(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                        value = login,
                        onValueChange = { login = it },
                        maxLines = 1,
                        isError = hasErrorlog,
                        textStyle = TextStyle(color = Color(0xFF61E8E1)),
                        label = { Text("Логин") },
                        supportingText = {
                            if (hasErrorlog) {
                                Text("Ошибка: поле не должно быть пустым", color = Color.Red)
                            }
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedLabelColor = Color.White,
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,)
                    )
                    OutlinedTextField(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                        value = password,

                        onValueChange = { password = it },
                        label = { Text("Пароль") },
                        maxLines = 1,
                        isError = hasErrorpas,
                        textStyle = TextStyle(color = Color(0xFF61E8E1)),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        supportingText = {
                            if (hasErrorpas) {
                                Text("Ошибка: поле не должно быть пустым", color = Color.Red)
                            }
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedLabelColor = Color.White,
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,)

                    )
                    var context = LocalContext.current
                    Spacer(modifier = Modifier.padding(40.dp))
                    Button(onClick = {
                        if(usermap.containsKey(login)) {
                            val storedPassword = usermap[login]
                            if(storedPassword == password)
                            {
                                //Toast.makeText(context,"вход", Toast.LENGTH_SHORT).show()
                                navController.navigate("screen2")
                            }
                            else
                            {
                                Toast.makeText(context,"Ошибка", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else
                        {
                            if(login.isEmpty()||password.isEmpty())
                            {
                                Toast.makeText(context,"Ошибка", Toast.LENGTH_SHORT).show()
                            }
                            else
                            {
                                val count = (usermap.size+1).toLong();
                                mUserViewModel.addUser(User(count,login,password))

                                //Toast.makeText(context,"Пользователь создан$count", Toast.LENGTH_SHORT).show()

                            }
                        }
                    }
                        ,
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        shape = RoundedCornerShape(5.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 5.dp,
                            pressedElevation = 3.dp
                        ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF61E8E1)
                        )) {
                        Text(text = "Войти", fontSize = 20.sp)
                    }
                }
            }
        }


    }
}
@Composable
fun LoginHeader() {
    Text(
        text = "KinoPoisk",
        fontSize = 50.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF61E8E1)
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen2(navController: NavController,viewModel: FilmViewModel) {
    var keyWord by remember { mutableStateOf("") }
    var selectedItem by remember { mutableStateOf("") }
    val list = listOf("1994","1995","1996","1997","1998")
    var expanded by remember { mutableStateOf(false) }
    val icon = if (expanded){
        Icons.Filled.KeyboardArrowUp
    }
    else{
        Icons.Filled.KeyboardArrowDown
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Spacer(modifier = Modifier.padding(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.weight(2f))
                Text(modifier = Modifier.align(Alignment.CenterVertically),
                    text = "KinoPoisk",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF61E8E1),

                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    navController.navigate("screen1")
                },modifier = Modifier.padding(12.dp)) {
                    Icon(
                        imageVector = Icons.Filled.ExitToApp,
                        contentDescription = "Назад",
                        tint = Color(0xFF61E8E1)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                IconButton(onClick = {
                    
                },modifier = Modifier.padding(12.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_swap_vert_24),
                        contentDescription = "Swap Vertical",
                        tint = Color(0xFF61E8E1)
                    )
                }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    value = keyWord,
                    onValueChange = { keyWord = it },
                    maxLines = 1,
                    textStyle = TextStyle(color = Color(0xFF61E8E1)),
                    trailingIcon = {
                        Icon(imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon",
                            tint = Color(0xFF61E8E1))
                    },
                    label = { Text("keyword") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,)
                )
            }
            ExposedDropdownMenuBox(modifier = Modifier.background(Color.Black),
                expanded = expanded
                , onExpandedChange = {expanded = it})
            {
                OutlinedTextField(
                    value = selectedItem,
                    onValueChange ={},
                    readOnly = true,
                    textStyle = TextStyle(color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    label = {},
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedLabelColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White,
                        focusedTrailingIconColor = Color(0xFF61E8E1)))
                ExposedDropdownMenu(modifier = Modifier
                    .background(Color.Black)
                    .border(1.dp, Color.White),expanded = expanded,
                    onDismissRequest = { expanded = false
                    }
                ){
                    list.forEachIndexed { index, label ->
                        DropdownMenuItem(
                            text = { Text(text = label, style = TextStyle(Color.White)) },
                            onClick = {
                                selectedItem = list[index]
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
            ViewFilms(viewModel = viewModel)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Proj1Theme {

    }
}
@Composable
fun ViewFilms(viewModel: FilmViewModel)
{
    val films by viewModel.films.observeAsState(initial = emptyList())
    LaunchedEffect(Unit) {
        viewModel.fetchfilms()
    }
    Column {
        if (films.isEmpty()) {
            // Show loading indicator or placeholder
            Text(text = "Loading...", color = Color.White)
        } else {
            // Display the list of credit cards
            FilmsArray(filmsArray = films)
        }
    }
}
@Composable
fun FilmsArray(filmsArray:List<Film>,modifier: Modifier = Modifier)
{
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        val itemCount = filmsArray.size
        items(itemCount) {
            ColumnItem(modifier,filmsArray,it)
        }
    }
}
@Composable
fun ColumnItem(modifier: Modifier, filmsArray: List<Film>, itemIndex:Int)
{
    val b:String = GetGenre(filmsArray[itemIndex].genres)
    val a:String = GetCountry(filmsArray[itemIndex].countries)
    Card(
        modifier
            .padding(10.dp)
            .wrapContentSize(),
        colors = CardDefaults.cardColors(
            contentColor = Color.White,
            containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(10.dp)
    )
    {
        Row(modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(15.dp)) {
            Image(
                painter = rememberAsyncImagePainter(filmsArray[itemIndex].posterUrlPreview),
                contentDescription = filmsArray[itemIndex].nameOriginal,
                modifier.size(140.dp))
            Column(modifier.padding(12.dp)) {
                Text(text = filmsArray[itemIndex].nameOriginal, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(text = b, fontSize = 16.sp)
                Row {
                    Text(text = a + ", ", fontSize = 16.sp)
                    Text(text = filmsArray[itemIndex].year.toString(), fontSize = 16.sp)
                }
                Text(modifier = Modifier.align(Alignment.End), text = filmsArray[itemIndex].ratingImdb.toString(), fontSize = 22.sp, color = Color(0xFF61E8E1), fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}
fun GetGenre(list:List<Genre>):String
{
    var arS : String = ""
    list.forEachIndexed {index, text ->
        if(text.genre.isEmpty())
        {
            return  arS
        }
        arS += text.genre
        if(index < list.size -1) {
            arS+=", "
        }
    }
    return arS
}
fun GetCountry(list:List<Country>):String
{
    var arS : String = ""

    list.forEachIndexed {index, text ->
        if(text.country.isEmpty())
        {
            return  arS
        }
        arS += text.country
        if(index < list.size -1) {
            arS+=", "
        }
    }
    return arS
}
