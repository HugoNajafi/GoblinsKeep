package com.goblinskeep.App;


public enum CellType {
    Empty,    // Empty walkable space
    Wall,     // Wall/obstacle (X)
    Key,      // Key (% - regular points)
    Bonus,    // Bonus ($ - bonus points)
    Trap,     // Trap (& - damages player)
    Lever,    // Lever (L - activates something)
    Entry,    // Entry point (@ - starting point)
    Exit      // Exit point (! - finish point)
    // Note: Player (O) and Enemy (E) are entities, not cell types
}
