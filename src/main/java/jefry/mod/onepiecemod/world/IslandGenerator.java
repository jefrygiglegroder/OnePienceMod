package jefry.mod.onepiecemod.world;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.util.RandomSource;

import java.util.Random;

public class IslandGenerator {
    private static final int MIN_ISLAND_SIZE = 20;
    private static final int MAX_ISLAND_SIZE = 50;
    private static final int MIN_ISLAND_HEIGHT = 10;
    private static final int MAX_ISLAND_HEIGHT = 25;
    private static final double ISLAND_SPAWN_CHANCE = 0.02; // 2% chance per chunk

    private static MinecraftServer server;

    public static void init(MinecraftServer minecraftServer) {
        server = minecraftServer;
    }

    public static void generateIsland(ChunkGenerator generator, BlockPos pos, RandomSource random) {
        if (random.nextDouble() > ISLAND_SPAWN_CHANCE) {
            return;
        }

        int islandSize = random.nextInt(MAX_ISLAND_SIZE - MIN_ISLAND_SIZE) + MIN_ISLAND_SIZE;
        int islandHeight = random.nextInt(MAX_ISLAND_HEIGHT - MIN_ISLAND_HEIGHT) + MIN_ISLAND_HEIGHT;

        // Generate base island shape
        generateIslandBase(pos, islandSize, islandHeight, random);

        // Add details
        addBeach(pos, islandSize);
        addVegetation(pos, islandSize, random);
        addStructures(pos, islandSize, random);
    }

    private static void generateIslandBase(BlockPos center, int size, int height, RandomSource random) {
        // Generate perlin noise for natural-looking terrain
        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                double distance = Math.sqrt(x * x + z * z) / size;
                if (distance <= 1.0) {
                    double noise = (1 - distance) * height;
                    int actualHeight = (int) (noise + random.nextDouble() * 2);

                    for (int y = 0; y < actualHeight; y++) {
                        BlockPos pos = center.offset(x, y, z);
                        if (y == actualHeight - 1) {
                            server.getLevel(center.getLevel().dimension()).setBlock(pos, Blocks.GRASS_BLOCK.defaultBlockState(), 3);
                        } else if (y > actualHeight - 4) {
                            server.getLevel(center.getLevel().dimension()).setBlock(pos, Blocks.DIRT.defaultBlockState(), 3);
                        } else {
                            server.getLevel(center.getLevel().dimension()).setBlock(pos, Blocks.STONE.defaultBlockState(), 3);
                        }
                    }
                }
            }
        }
    }

    private static void addBeach(BlockPos center, int size) {
        for (int x = -size - 2; x <= size + 2; x++) {
            for (int z = -size - 2; z <= size + 2; z++) {
                double distance = Math.sqrt(x * x + z * z) / size;
                if (distance <= 1.2 && distance > 0.8) {
                    BlockPos pos = center.offset(x, 0, z);
                    int maxY = 5;
                    for (int y = 0; y < maxY; y++) {
                        server.getLevel(center.getLevel().dimension()).setBlock(pos.offset(0, y, 0), Blocks.SAND.defaultBlockState(), 3);
                    }
                }
            }
        }
    }

    private static void addVegetation(BlockPos center, int size, RandomSource random) {
        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                double distance = Math.sqrt(x * x + z * z) / size;
                if (distance <= 0.8 && random.nextDouble() < 0.1) {
                    BlockPos surfacePos = findSurfaceBlock(center.offset(x, 0, z));
                    if (surfacePos != null) {
                        if (random.nextDouble() < 0.3) {
                            generateTree(surfacePos, random);
                        } else {
                            server.getLevel(center.getLevel().dimension()).setBlock(surfacePos.above(), Blocks.GRASS.defaultBlockState(), 3);
                        }
                    }
                }
            }
        }
    }

    private static void generateTree(BlockPos pos, RandomSource random) {
        int height = random.nextInt(4) + 4;

        // Generate trunk
        for (int y = 0; y < height; y++) {
            server.getLevel(pos.getLevel().dimension()).setBlock(pos.offset(0, y, 0), Blocks.OAK_LOG.defaultBlockState(), 3);
        }

        // Generate leaves
        for (int x = -2; x <= 2; x++) {
            for (int y = height - 3; y <= height; y++) {
                for (int z = -2; z <= 2; z++) {
                    if (Math.abs(x) + Math.abs(z) < 4 && random.nextDouble() < 0.7) {
                        BlockPos leafPos = pos.offset(x, y, z);
                        server.getLevel(pos.getLevel().dimension()).setBlock(leafPos, Blocks.OAK_LEAVES.defaultBlockState(), 3);
                    }
                }
            }
        }
    }

    private static void addStructures(BlockPos center, int size, RandomSource random) {
        // Add random small structures (can be expanded based on needs)
        if (random.nextDouble() < 0.3) { // 30% chance for a structure
            int x = random.nextInt(size * 2) - size;
            int z = random.nextInt(size * 2) - size;
            BlockPos structurePos = findSurfaceBlock(center.offset(x, 0, z));
            if (structurePos != null) {
                generateSimpleStructure(structurePos, random);
            }
        }
    }

    private static void generateSimpleStructure(BlockPos pos, RandomSource random) {
        // Generate a simple house or shelter
        int width = random.nextInt(2) + 3;
        int depth = random.nextInt(2) + 3;
        int height = random.nextInt(2) + 3;

        // Generate walls
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    if (x == 0 || x == width - 1 || z == 0 || z == depth - 1 || y == height - 1) {
                        server.getLevel(pos.getLevel().dimension()).setBlock(pos.offset(x, y, z),
                                Blocks.OAK_PLANKS.defaultBlockState(), 3);
                    }
                }
            }
        }

        // Add door
        server.getLevel(pos.getLevel().dimension()).setBlock(pos.offset(width / 2, 0, 0),
                Blocks.OAK_DOOR.defaultBlockState(), 3);
        server.getLevel(pos.getLevel().dimension()).setBlock(pos.offset(width / 2, 1, 0),
                Blocks.OAK_DOOR.defaultBlockState(), 3);
    }

    private static BlockPos findSurfaceBlock(BlockPos pos) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        for (int y = 100; y > 0; y--) {
            mutablePos.setY(y);
            if (server.getLevel(pos.getLevel().dimension()).getBlockState(mutablePos).getBlock() == Blocks.GRASS_BLOCK) {
                return mutablePos.immutable();
            }
        }
        return null;
    }
}