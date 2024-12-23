package com.example.draggableobject


import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.draggableobject.components.AnimatedItem
import com.example.draggableobject.components.BoardTextField
import kotlin.math.roundToInt


@Composable
fun MyBoard() {
    val nodes = remember {
        mutableStateListOf(
            NodeItem(title = "P 1"),
            NodeItem(title = "P 2"),
        )
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                nodes.forEach { node ->
                    MultipleDraggableObject(
                        nodeItem = node,
                        onDeleteNode = { nodeId ->
                            nodes.removeIf { n ->
                                n.id == nodeId
                            }
                        },
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        nodes.add(NodeItem(title = "baby"))
                    },
                    Modifier
                        .background(color = Color.Gray, shape = CircleShape)
                        .padding(end = 15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "add node",
                        tint = Color.White
                    )
                }
            }
        }
    }

}

//composable function for DraggableObject
@Composable
fun MultipleDraggableObject(
    nodeItem: NodeItem, onDeleteNode: (nodeId: String) -> Unit
) {
    val offsetX = remember { mutableFloatStateOf(0f) }
    val offsetY = remember { mutableFloatStateOf(0f) }

    val content = remember { mutableStateOf(nodeItem.title) }

    val isAnimatedItemPlaying =
        remember { mutableStateOf(false) } // Track the playing state of Lottie


    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    x = offsetX.floatValue.roundToInt(),
                    y = offsetY.floatValue.roundToInt()
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        isAnimatedItemPlaying.value = true
                    },
                    onDragEnd = {
                        isAnimatedItemPlaying.value = false
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offsetX.floatValue += dragAmount.x
                        offsetY.floatValue += dragAmount.y
                    }
                )
            }
            .size(200.dp)
    ) {
        AnimatedItem(penguin = nodeItem.penguin, isPlaying = isAnimatedItemPlaying.value)
        IconButton(modifier = Modifier
            .align(Alignment.TopCenter)
            .background(Color.Red)
            .size(20.dp), onClick = {
            onDeleteNode.invoke(nodeItem.id)
        }) {
            Icon(
                imageVector = Icons.Default.Close, contentDescription = "delete Node",
                tint = Color.White
            )
        }
        BoardTextField(
            text = content.value,
            onValueChange = {
                content.value = it

            }, textStyle = MaterialTheme.typography.titleMedium
        )

    }
}


