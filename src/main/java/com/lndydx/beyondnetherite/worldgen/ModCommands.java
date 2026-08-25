package com.lndydx.beyondnetherite.worldgen;

import com.lndydx.beyondnetherite.BeyondNetherite;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class ModCommands {

    public static void initialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("placeruins")
                    .executes(ModCommands::placeRuins));
            dispatcher.register(Commands.literal("findruins")
                    .executes(ModCommands::findRuins));
        });
    }

    private static int placeRuins(CommandContext<CommandSourceStack> context) {
        ServerLevel level = context.getSource().getLevel();
        BlockPos p = context.getSource().getPlayer().blockPosition();
        int x0 = p.getX() - 12;
        int z0 = p.getZ() - 11;
        int y = level.getHeight(Heightmap.Types.WORLD_SURFACE, x0, z0);

        StructureTemplate template = level.getStructureManager()
                .getOrCreate(BeyondNetherite.id("shade_ruins"));
        StructurePlaceSettings settings = new StructurePlaceSettings();
        settings.setRandom(level.getRandom());

        BlockPos pos = new BlockPos(x0, y, z0);
        template.placeInWorld(level, pos, pos, settings, level.getRandom(), 2);
        context.getSource().sendSuccess(() -> Component.literal("§5Shade Ruins placed!"), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int findRuins(CommandContext<CommandSourceStack> context) {
        ServerLevel level = context.getSource().getLevel();
        ServerPlayer player = context.getSource().getPlayer();
        BlockPos origin = player.blockPosition();
        int cx0 = origin.getX() >> 4;
        int cz0 = origin.getZ() >> 4;

        context.getSource().sendSuccess(
                () -> Component.literal("§5Searching for Shade Ruins..."), true);

        for (int r = 0; r <= 24; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (Math.max(Math.abs(dx), Math.abs(dz)) != r) continue;
                    LevelChunk chunk = level.getChunk(cx0 + dx, cz0 + dz);
                    if (countCryingObsidian(chunk) >= 12) {
                        int tx = (cx0 + dx) * 16 + 8;
                        int tz = (cz0 + dz) * 16 + 8;
                        int ty = level.getHeight(Heightmap.Types.WORLD_SURFACE, tx, tz);
                        player.setPos(tx + 0.5, ty + 1, tz + 0.5);
                        context.getSource().sendSuccess(
                                () -> Component.literal("§5Shade Ruins found at " + tx + ", " + tz), true);
                        return Command.SINGLE_SUCCESS;
                    }
                }
            }
        }
        context.getSource().sendSuccess(
                () -> Component.literal("§cNot found within 384 blocks"), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int countCryingObsidian(LevelChunk chunk) {
        int count = 0;
        for (LevelChunkSection section : chunk.getSections()) {
            if (section == null || section.hasOnlyAir()) continue;
            if (!section.getStates().maybeHas(s -> s.is(Blocks.CRYING_OBSIDIAN))) continue;
            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 16; y++) {
                    for (int z = 0; z < 16; z++) {
                        if (section.getBlockState(x, y, z).is(Blocks.CRYING_OBSIDIAN)) {
                            count++;
                            if (count >= 12) return count;
                        }
                    }
                }
            }
        }
        return count;
    }
}