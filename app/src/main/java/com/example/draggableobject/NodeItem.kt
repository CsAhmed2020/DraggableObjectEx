package com.example.draggableobject

import java.util.UUID

data class NodeItem(val id:String = UUID.randomUUID().toString(), val title:String,val penguin:Int = penguins.random())

val penguins = listOf(
    R.raw.penguin_a,
    R.raw.penguin_b
)