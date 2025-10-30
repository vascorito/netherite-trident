package net.vascorito;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.math.Vec3d;

public class ModAttachments {
    public static void initialise() {}

    public static final AttachmentType<Integer> TRIDENT_SLOT_ATTACHMENT = AttachmentRegistry.create(NetheriteTrident.id("trident_slot"));
}
