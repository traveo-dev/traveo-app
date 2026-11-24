package com.traveo.app.feature.trips.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traveo.app.R
import com.traveo.app.core.designsystem.theme.TraveoTheme

private data class MemberItem(
    val name: String,
    val email: String,
    val role: String
)

private val SampleMembers = listOf(
    MemberItem("Andrii Bielov", "You · created the trip", "Owner"),
    MemberItem("Kate Melnyk", "kate.m@gmail.com", "Editor"),
    MemberItem("Denys Kovalenko", "denys.k@gmail.com", "Editor"),
    MemberItem("Olha Serhiienko", "olha.s@gmail.com", "Viewer")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripMembersScreen(
    onBackClick: () -> Unit = {},
    onInviteMoreClick: () -> Unit = {}
) {
    var selectedMemberForRights by remember { mutableStateOf<MemberItem?>(null) }
    var activeRole by remember { mutableStateOf("Editor") }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "Members",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                IconButton(onClick = onInviteMoreClick) {
                    Text(text = "+", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }

            Text(
                text = "Poland Road Trip · 4 people",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                fontSize = 12.sp
            )

            // Members List Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp)),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Column {
                    SampleMembers.forEachIndexed { index, member ->
                        MemberRow(
                            name = member.name,
                            subtitle = member.email,
                            role = member.role,
                            onClick = {
                                selectedMemberForRights = member
                                activeRole = member.role
                            }
                        )
                        if (index < SampleMembers.lastIndex) {
                            HorizontalDivider(color = Color(0x0F000000))
                        }
                    }
                }
            }

            // Pending Invites Section
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Pending invites",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    fontSize = 12.sp
                )

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp)),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Column {
                        PendingInviteRow(email = "maks.i@gmail.com", time = "Sent 2 days ago")
                        HorizontalDivider(color = Color(0x0F000000))
                        PendingInviteRow(email = "+48 512 004 118", time = "Sent yesterday")
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Invite More People Button
            Button(
                onClick = onInviteMoreClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Invite more people",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White
                )
            }
        }

        // Access Rights Modal Bottom Sheet
        if (selectedMemberForRights != null) {
            val member = selectedMemberForRights!!

            ModalBottomSheet(
                onDismissRequest = { selectedMemberForRights = null },
                sheetState = sheetState,
                containerColor = Color.White,
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Member Profile Info Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE8EAF6)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "👤", fontSize = 26.sp)
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = member.name,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = if (member.email.contains("@")) member.email else "kate.m@gmail.com",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                    }

                    // Section Title
                    Text(
                        text = "Access rights",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )

                    // Access Rights Options List
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        AccessRightOptionCard(
                            icon = "👑",
                            title = "Owner",
                            subtitle = "Full control, can delete the trip",
                            isSelected = activeRole == "Owner",
                            onClick = { activeRole = "Owner" }
                        )

                        AccessRightOptionCard(
                            icon = "✏️",
                            title = "Editor",
                            subtitle = "Can add, edit and delete events",
                            isSelected = activeRole == "Editor",
                            onClick = { activeRole = "Editor" }
                        )

                        AccessRightOptionCard(
                            icon = "👁️",
                            title = "Viewer",
                            subtitle = "Can only view the trip and chat",
                            isSelected = activeRole == "Viewer",
                            onClick = { activeRole = "Viewer" }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Remove From Trip Button
                    Button(
                        onClick = { selectedMemberForRights = null },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFF0F0)
                        )
                    ) {
                        Text(
                            text = "Remove from trip",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color(0xFFFF3B30),
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun AccessRightOptionCard(
    icon: String,
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    val backgroundColor = if (isSelected) Color(0xFFF2F7FF) else MaterialTheme.colorScheme.surfaceVariant

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .border(
                width = if (isSelected) 1.5.dp else 0.dp,
                color = borderColor,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = icon, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        fontSize = 11.sp
                    )
                }
            }

            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "✓", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.LightGray, CircleShape)
                )
            }
        }
    }
}

@Composable
private fun MemberRow(
    name: String,
    subtitle: String,
    role: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE8EAF6)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "👤", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    fontSize = 11.sp
                )
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(
                        if (role == "Owner") MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surfaceVariant
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = role,
                    fontSize = 11.sp,
                    color = if (role == "Owner") Color.White else Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_right),
                contentDescription = "Edit Role",
                tint = Color.LightGray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun PendingInviteRow(email: String, time: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "✉️", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = time,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    fontSize = 11.sp
                )
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { }
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(text = "Resend", fontSize = 11.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "✕", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.clickable { })
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TripMembersScreenPreview() {
    TraveoTheme {
        TripMembersScreen()
    }
}
