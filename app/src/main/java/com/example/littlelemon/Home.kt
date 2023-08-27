package com.example.littlelemon

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.RequestBuilderTransform
import com.example.littlelemon.ui.theme.green

@Composable
fun Home(
        navController : NavHostController,
        menuList : List<MenuItemEntity>,
        androidViewModel : AndroidViewModel
) {
    var searchPhrase by rememberSaveable { mutableStateOf("") }

    Column(
            modifier = Modifier.fillMaxWidth()

    ) {
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                verticalAlignment = Alignment.CenterVertically

        ) {
            Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .scale(3.5f)
            )

            Image(painter = painterResource(id = R.drawable.profile),
                  contentDescription = null,
                  modifier = Modifier
                      .clickable { navController.navigate(Profile.route) }
                      .scale(.6f)


            )


        }



        Column(
                modifier = Modifier
                    .background(Color(0xFF495E57))
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp)
        ) {
            Text(
                    text = stringResource(id = R.string.title),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF4CE14)
            )
            Text(
                    text = stringResource(id = R.string.location),
                    fontSize = 18.sp,
                    color = Color(0xFFEDEFEE)
            )
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
            ) {
                Text(
                        text = stringResource(id = R.string.description),
                        color = Color(0xFFEDEFEE),
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth(0.6f)
                )
                Image(
                        painter = painterResource(id = R.drawable.hero_image),
                        contentDescription = "Upper Panel Image",
                        modifier = Modifier
                            .height(100.dp)
                            .width(100.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                )

            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
            ) {
                TextField(value = searchPhrase,
                          onValueChange = { searchPhrase = it },
                          label = { Text(text = "Enter search phrase") },
                          leadingIcon = {
                              Image(
                                      imageVector = Icons.Rounded.Search, contentDescription = null
                              )
                          },
                          singleLine = true,
                          modifier = Modifier.fillMaxWidth()
                )
            }

        }
        var menuItems = if (searchPhrase.isNotEmpty()) {
            menuList.filter { it.title.contains(searchPhrase, ignoreCase = true) }
        }
        else {
            menuList
        }
        if (androidViewModel.category.isNotEmpty()) {
            when (androidViewModel.category) {
                "starters" -> {
                    androidViewModel.resetAlphaValues()
                    menuItems = menuItems.filter { it.category == "starters" }
                    androidViewModel.startersAlpha = 0.5f
                }

                "mains" -> {
                    androidViewModel.resetAlphaValues()
                    menuItems = menuItems.filter { it.category == "mains" }
                    androidViewModel.mainsAlpha = .5f
                }

                "desserts" -> {
                    androidViewModel.resetAlphaValues()
                    menuItems = menuItems.filter { it.category == "desserts" }
                    androidViewModel.dessertsAlpha = 0.5f
                }

                "drinks" -> {
                    androidViewModel.resetAlphaValues()
                    menuItems = menuItems.filter { it.category == "drinks" }
                    androidViewModel.drinksAlpha = 0.5f
                }
            }
        }
        MenuItems(menuItems = menuItems, androidViewModel = androidViewModel)
    }
}

@Composable
fun OrderForDelivery(androidViewModel : AndroidViewModel) {

    Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
    ) {
        Row {

            Spacer(modifier = Modifier.width(14.dp))
            Text(
                    text = "ORDER FOR DELIVERY!", fontWeight = FontWeight.Bold, fontSize = 20.sp
            )

        }
        Spacer(modifier = Modifier.height(10.dp))

        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
        ) {
            Image(imageVector = Icons.Rounded.Refresh,
                  contentDescription = null,
                  modifier = Modifier.clickable {
                          androidViewModel.category = ""
                          androidViewModel.resetAlphaValues()
                      })
            Text(text = "Starters",
                 fontWeight = FontWeight.Bold,
                 fontSize = 14.sp,
                 color = green,
                 modifier = Modifier
                     .clip(RoundedCornerShape(14.dp))
                     .background(green.copy(alpha = androidViewModel.startersAlpha))
                     .padding(8.dp)
                     .clickable { androidViewModel.category = "starters" }

            )
            Text(text = "Mains",
                 fontWeight = FontWeight.Bold,
                 fontSize = 14.sp,
                 color = green,
                 modifier = Modifier
                     .clip(RoundedCornerShape(14.dp))
                     .background(green.copy(androidViewModel.mainsAlpha))
                     .padding(8.dp)
                     .clickable { androidViewModel.category = "mains" })
            Text(text = "Desserts",
                 fontWeight = FontWeight.Bold,
                 fontSize = 14.sp,
                 color = green,
                 modifier = Modifier
                     .clip(RoundedCornerShape(14.dp))
                     .background(green.copy(androidViewModel.dessertsAlpha))
                     .padding(8.dp)
                     .clickable { androidViewModel.category = "desserts" })
            Text(text = "Drinks",
                 fontWeight = FontWeight.Bold,
                 fontSize = 14.sp,
                 color = green,
                 modifier = Modifier
                     .clip(RoundedCornerShape(14.dp))
                     .background(green.copy(androidViewModel.drinksAlpha))
                     .padding(8.dp)
                     .clickable { androidViewModel.category = "drinks" })

        }
        Spacer(modifier = Modifier.height(14.dp))
        Divider()
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Glide(item : MenuItemEntity) {
    GlideImage(
            model = item.image,
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(84.dp)
                .clip(RoundedCornerShape(8.dp))
    )
}


@Composable
fun MenuItem(item : MenuItemEntity) {
    Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
    ) {
        Text(text = item.title, fontWeight = FontWeight.Bold)
        Text(text = item.description, maxLines = 2, overflow = TextOverflow.Ellipsis)
        Text(text = "Price: $${item.price}")
    }
}

@Composable
fun MenuItemWithImage(item : MenuItemEntity) {
    Row(
            modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically
    ) {


        Row(modifier = Modifier.weight(.75f)) {
            MenuItem(item = item)
        }
        Row(modifier = Modifier.weight(.25f)) {
            Glide(item = item)
        }

    }
}


@Composable
fun MenuItems(menuItems : List<MenuItemEntity>, androidViewModel : AndroidViewModel) {
    Column(
            modifier = Modifier
                .padding(PaddingValues(14.dp))
                .verticalScroll(rememberScrollState())

    ) {
        OrderForDelivery(androidViewModel = androidViewModel)

        for (menuItem in menuItems) {
            MenuItemWithImage(item = menuItem)
            Divider()
        }
    }
}