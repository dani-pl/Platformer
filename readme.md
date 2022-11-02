Author: Daniel Peña López

Date: October 2022

**G improvements**

-  *G improvement 2:* Create a static "enemy" (think: spikes or lava) - an obstacle to avoid,
   which deals damage to the Player when touched .This feature needs to be holistic! There should
   be gameplay consequences of taking damage (for example, the player can have health, and be able 
   to take damage and die). Recovery frames are necessary (a brief period of invulnerability after 
   taking damage). Sound effects. All these aspects ensure that the feature is communicated clearly
   to the player.
   
   *Implementation:* I have created a static enemy which are spikes. Basically, they lie on the floor
   and if the player touch them, the player loses a life, there are of course sound effects and
   recovery frames. There are a total of 3 lifes. In case, the 3 lifes are lost, the player dies.
   In the second level (there are two), I have created a dynamic enemy which are blocks which are 
   jumping and if they jump over the player, the player loses another life.

-  *G improvement 3:* Create a dynamic collectible Entity (think: coins or stars). Something that we
   can pick up by colliding with it, and that has physics. (eg. gravity, velocities etc apply to 
   these objects). Like feature 2 (and all other features) - make sure it's fully implemented.
   Ergo: it is communicated clearly to the player, it makes sounds when picked up, the UI provides
   information, etc.


   *Implementation:* I use coins for the dynamic collectible for both levels. They have physic 
   because they are constantly jumping and the player needs to take them. There is a sound effect
   when picking and the Render HUD displays the number of collectibles on the top of the screen.

-  *G improvement 4:* Create a HUD to display the number of collectibles, player health, etc.
   It should display (at least) the number of collectibles left in the world, and the number of 
   collectibles picked up so far. You can use either the Android layout system, or manual
   bitmap-rendering in the game engine.


   *Implementation:* The HUD displays both the player health and the number of collectibles (both total
   and collected). I have used manual bitmap rendering for this purpose. We are constantly rendering
   bitmaps in this game, so I have used that logic to render a coin and some hearts that helps the 
   player understand the information about these statistics.

-  *G improvement 5:* Replace our TestLevel with a level layout loaded from the resources (XML) 
   or assets folder (a simple plaintext file is fine!).

   *Implementation:* I have used simple plaintexts that you can find in the assets folder.

-  *G improvement 6:* Add audio! We want background music for each level and sound effects for all 
   of the important interactions! It's a good idea to have different background music for each level.

   *Implementation:* All important interactions have sound effects: block hits the player, coin is 
   collected, player dies, player jumps, player touches spikes, start of the level and complete
   successfully a level. There are also two backgrounds musics, one for each level.

**VG improvements**

-  *VG improvement 1:* Implement player win - loading a new level when she gathers all collectibles
   / destroys all enemies / reaches a goal. This mean: make a second level, with different 
   assets and layout. When we win the second level, it's okay to load the first again.


   *Implementation:* A second level with different assets has been created, after that level I show
   the first level again.

-  *VG improvement 5:* Add animated entities! Give your player a walk cycle. Look up: sprite sheet
   animation, or the AnimationDrawable (Links to an external site.). Either solution will
   require a bit of scaffolding - your entity must know what frame is displaying, and the width and 
   height of each frame of animation. It needs to be able to pause animation (and potentially slow 
   down / speed up - walk cycles look better if they're responding to player input).


   *Implementation:* I have used a simpler solution by which I include the two other images that 
   complete the walk cycle of the player as parameters in its constructor and then I just keep 
   changing the sprite to show for each render, but I only change the sprite to show in case the user
   is pressing the moving keypad which means and the player is actually moving.