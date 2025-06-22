# Jukebox Looping

![The Jukebox Looping mod icon](src/main/resources/assets/jukebox_looping/icon_high_res.png)

Changes Jukeboxes so that when a disc finishes playing, instead of just stopping, the song loops.

The mod doesn't need to be installed client-side, it works fine when installed only on the server.

Jukeboxes won't loop if they're placed above a block entity.
This allows redstone creations like disc shufflers to continue working, since hoppers are block entities,
and if the song doesn't loop it can simply take the disc out as normal.
This can also be used to make non-looping jukeboxes if you need to;
simply place it above a block entity that doesn't interact with Jukeboxes (e.g. Barrels) and the song won't loop.