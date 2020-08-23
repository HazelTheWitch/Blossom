# 🌸 Blossom 

**This project is a work in progress.**

## Description
Blossom is a library mod, mainly for personal use, which allows you to group items by identifier. For example a block `yourmod:yourblock` and block entity `yourmod:yourblock` would be grouped into one [BGroup](src/main/java/com/hazeltrinity/blossom/init/BGroup.java). Make sure you only use one instance of [BMod](src/main/java/com/hazeltrinity/blossom/BMod.java) in your mod, and inherit from [BModInitializer](src/main/java/com/hazeltrinity/blossom/init/BModInitializer.java) for your entrypoints.

Further it allows you to make custom GUIs, similar to [LibGui](https://github.com/CottonMC/LibGui), with the main draw to Blossom being, in the future having "Styles" similar to css to customize your entire GUI at runtime easily.

## Features
 - Initialization Package
   - `BMod` for handling logging, initialization, and identifiers.
   - `BGroup` to handle all registration of blocks, items, block entities, etc.
   - `BModInitializer` subclass to automatically initialize everything registered to the `BMod` instance.
 - GUI Package
   - Widget based GUI design.
     - `Panel` widgets structure the GUI.
     - `BWidgets` draw and manipulate data.
   - *Event system
   - *Style system
  
**planned feature*

## Feedback
If you have any issues, feedback, or suggestions, please feel free to make an issue, or add me on Discord, Hazel#0720.