package com.lndydx.beyondnetherite.worldgen;

import com.lndydx.beyondnetherite.BeyondNetherite;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public class ModStructures {
    public static final StructurePieceType SHADE_RUINS_PIECE =
            (context, tag) -> new ShadeRuinsPiece(context, tag);

    public static void initialize() {
        Registry.register(BuiltInRegistries.STRUCTURE_TYPE,
                BeyondNetherite.id("shade_ruins"), ShadeRuinsStructure.TYPE);
        Registry.register(BuiltInRegistries.STRUCTURE_PIECE,
                BeyondNetherite.id("shade_ruins_piece"), SHADE_RUINS_PIECE);
    }
}