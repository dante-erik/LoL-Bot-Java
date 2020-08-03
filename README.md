# LoL-Bot
Plays League of Legends' Champions Fully Autonomously
-
Authors: Erik, Dante

## Instructions For User
In the current patch, LoL-Bot only works on [1920x1080] monitor resolution, and [1280x720] League of Legends Client resolution.

Note:
* Higher resolution monitors can scale down to [1920x1080] in "Display settings", accessed by right clicking on the desktop.
* LoL-Bot will work properly after changing to [1920x1080] monitor resolution and [1280x720] Client resolution.

Two methods for changing to the correct in-game League of Legends settings are listed below.

Complete method 1 -OR- method 2 for LoL-Bot to work properly while in-game.

### Method 1: Manually change settings while in game or through the client's "In-Game" settings tabs.

*(Required)*
1. While in game, hit [Esc] to open the settings menu.
2. Reset all in-game settings to default. (left click "Restore Defaults" at the top right on each settings tab.)
3. Select the "GAME" tab.
4. Disable "Attack move on cursor".
5. Select the "Interface" tab.
6. Disable "Show Health Bar Animations".

*(Optional - Recommended)*
1. Select the "HOTKEYS" tab.
2. Disable "Replace Quick Cast with Quick Cast With Indicator in the quickbind UI".

Or, complete Method 2.

### Method 2: Replacing "PersistedSettings.json".
1. Login to the League of Legends Client. (BUT DO NOT ENTER A GAME.)
2. Replace "PersistedSettings.json" in "C:\Riot Games\League of Legends\Config" with the "PersistedSettings.json" in the "data" folder in "LoL-Bot".
