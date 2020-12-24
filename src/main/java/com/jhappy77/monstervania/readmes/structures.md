# How to make structures

This guide will quickly describe how to create and load a structure into the mod.
## Getting started

Build the structure you would like to add. Typically, you will be doing
this in a forge build so that you can include modded assets.

Use IntelliJ to Run Client and launch a forge build. 
Create a new world, name it something like MyStructureWorld.
Use structure blocks and structure voids to create your structure.
You will use SAVE mode and CORNER mode to save a structure, and you
can use LOAD mode to test it out. Make sure to include Structure voids 
where necessary.

Useful commands:
```
/give @p minecraft:structure_block 1

/give @p minecraft:structure_void 1 
```

Once you have saved your structures, you can access them via
their .nbt files. The path to the files will look something like:
```
D:\MonstervaniaMod\run\saves\MyStructureWorld\generated\monstervania\structures
```

## Loading it in

Place all nbt files in the following directory:
```
src/main/resources/data/structures
```

Create a new file in World/Structures/Pieces for your structure pieces. You can base this code off of
FrostSpiderPitPieces.java, and everywhere there is a TODO comment indicating you will need to replace some code,
replace the code with an analogous line for your structure.

Then, repeat this process, but now using FrostSpiderPitStructure. 

The next step is to add your structure to UnconfiguredStructures.java. Follow the pattern indicated by VampireTower
and FrostSpiderPit to add the structure, register it, add mapping, and register all pieces.

Next, add boilerplate code in ConfiguredStructures.java.

Finally, add a line of code in Monstervania.java in the addDimensionalSpacing function for your structure.

To get your structures to spawn, add lines in BiomeLoadReaction.java
