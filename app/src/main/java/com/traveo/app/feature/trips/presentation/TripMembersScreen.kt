package com.traveo.app.feature.trips.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traveo.app.R
import com.traveo.app.core.designsystem.theme.TraveoTheme

// Purple used for initials avatars. The theme has no token for it yet.
private val InitialsPurple = Color(0xFF7B4DFF)

private data class MemberItem(
    val name: String,
    val subtitle: String,
    val role: String,
    @DrawableRes val avatarRes: Int? = null,
    val initials: String? = null
)

private data class AccessRight(
    val role: String,
    @DrawableRes val iconRes: Int,
    val description: String
)

private val SampleMembers = listOf(
    MemberItem("Andrii Bielov", "You · created the trip", "Owner", avatarRes = R.drawable.img_profile_logo),
    MemberItem("Kate Melnyk", "kate.m@gmail.com", "Editor", avatarRes = R.drawable.img_profile_logo),
    MemberItem("Denys Kovalenko", "denys.k@gmail.com", "Editor", avatarRes = R.drawable.img_profile_logo),
    MemberItem("Olha Serhiienko", "olha.s@gmail.com", "Viewer", initials = "OS")
)

private val AccessRights = listOf(
    AccessRight("Owner", R.drawable.ic_crown, "Full control, can delete the trip"),
    AccessRight("Editor", R.drawable.ic_edit, "Can add, edit and delete events"),
    AccessRight("Viewer", R.drawable.ic_eye, "Can only view the trip and chat")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripMembersScreen(
    onBackClick: () -> Unit = {},
    onInviteMoreClick: () -> Unit = {}
) {
    var selectedMember by remember { mutableStateOf<MemberItem?>(null) }
    var activeRole by remember { mutableStateOf("Editor") }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
                Box(modifier = Modifier.fillMaxWidth()) {
                    CircleIconButton(
                        iconRes = R.drawable.ic_arrow_back,
                        contentDescription = "Back",
                        onClick = onBackClick,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )

                    Text(
                        text = "Members",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W400
                        ),
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    CircleIconButton(
                        iconRes = R.drawable.ic_add,
                        contentDescription = "Invite",
                        onClick = onInviteMoreClick,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }

                Text(
                    text = "Poland Road Trip · 4 people",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )

                // Members
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    SampleMembers.forEachIndexed { index, member ->
                        MemberRow(
                            member = member,
                            onClick = {
                                selectedMember = member
                                activeRole = member.role
                            }
                        )

                        if (index != SampleMembers.lastIndex) RowDivider()
                    }
                }

                // Pending invites
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = "Pending invites",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        PendingInviteRow(email = "maks.i@gmail.com", time = "Sent 2 days ago")
                        RowDivider()
                        PendingInviteRow(email = "+48 512 004 118", time = "Sent yesterday")
                    }
                }
            }

            // Pinned button
            Box(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
                    .fillMaxWidth()
                    .height(58.dp)
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable(onClick = onInviteMoreClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Invite more people",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }

        // Access rights sheet
        selectedMember?.let { member ->
            ModalBottomSheet(
                onDismissRequest = { selectedMember = null },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PersonAvatar(member = member, size = 50.dp)

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = member.name,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = member.subtitle,
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }

                    Text(
                        text = "Access rights",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        AccessRights.forEach { right ->
                            AccessRightCard(
                                right = right,
                                isSelected = activeRole == right.role,
                                onClick = { activeRole = right.role }
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(50))
                            .background(MaterialTheme.colorScheme.error.copy(alpha = 0.1f))
                            .clickable { selectedMember = null },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Remove from trip",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MemberRow(
    member: MemberItem,
    onClick: () -> Unit
) {
    val isOwner = member.role == "Owner"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PersonAvatar(member = member, size = 44.dp)

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = member.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = member.subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = MaterialTheme.colorScheme.outline,
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(
                    if (isOwner) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f)
                )
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            Text(
                text = member.role,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = if (isOwner) Color.White else MaterialTheme.colorScheme.outline,
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = "Edit role",
            tint = MaterialTheme.colorScheme.outline,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun PendingInviteRow(
    email: String,
    time: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_email_outline),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = time,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = MaterialTheme.colorScheme.outline
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f))
                .clickable { }
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Resend",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = MaterialTheme.colorScheme.outline,
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = "Cancel invite",
            tint = MaterialTheme.colorScheme.outline,
            modifier = Modifier
                .size(20.dp)
                .clickable { }
        )
    }
}

@Composable
private fun AccessRightCard(
    right: AccessRight,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val primary = MaterialTheme.colorScheme.primary
    val shape = RoundedCornerShape(24.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(if (isSelected) primary.copy(alpha = 0.06f) else MaterialTheme.colorScheme.surfaceVariant)
            .then(if (isSelected) Modifier.border(1.5.dp, primary, shape) else Modifier)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = right.iconRes),
                contentDescription = null,
                tint = if (isSelected) primary else MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = right.role,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = right.description,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = MaterialTheme.colorScheme.outline
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = "Selected",
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .border(1.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f), CircleShape)
            )
        }
    }
}

@Composable
private fun PersonAvatar(
    member: MemberItem,
    size: Dp
) {
    if (member.avatarRes != null) {
        Image(
            painter = painterResource(id = member.avatarRes),
            contentDescription = member.name,
            modifier = Modifier
                .size(size)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(InitialsPurple),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = member.initials.orEmpty(),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                color = Color.White
            )
        }
    }
}

@Composable
private fun CircleIconButton(
    @DrawableRes iconRes: Int,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun RowDivider() {
    HorizontalDivider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TripMembersScreenPreview() {
    TraveoTheme {
        TripMembersScreen()
    }
}