package game.model;

import game.engine.Constants;
import java.util.HashSet;
import java.util.Set;

public class BoardLayout {

    private static final Set<Integer> CARD_CELLS              = new HashSet<>();
    private static final Set<Integer> CONVEYOR_BELT_CELLS     = new HashSet<>();
    private static final Set<Integer> CONTAMINATION_SOCK_CELLS = new HashSet<>();
    private static final Set<Integer> MONSTER_CELLS           = new HashSet<>();

    static {
        for (int i : Constants.CARD_CELL_INDICES)    CARD_CELLS.add(i);
        for (int i : Constants.CONVEYOR_CELL_INDICES) CONVEYOR_BELT_CELLS.add(i);
        for (int i : Constants.SOCK_CELL_INDICES)    CONTAMINATION_SOCK_CELLS.add(i);
        for (int i : Constants.MONSTER_CELL_INDICES) MONSTER_CELLS.add(i);
    }

    public static CellType typeOf(int index) {
        if (index < 0 || index > 99)
            throw new IllegalArgumentException("Cell index must be 0-99, got: " + index);

        // Special cells take priority over the door pattern
        if (MONSTER_CELLS.contains(index))             return CellType.MONSTER;
        if (CARD_CELLS.contains(index))                return CellType.CARD;
        if (CONVEYOR_BELT_CELLS.contains(index))       return CellType.CONVEYOR_BELT;
        if (CONTAMINATION_SOCK_CELLS.contains(index))  return CellType.CONTAMINATION_SOCK;

        // Odd indices → doors, alternating SCARER / LAUGHER
        // cell 1 = SCARER, cell 3 = LAUGHER, cell 5 = SCARER ...
        if (index % 2 == 1) {
            int doorNumber = (index - 1) / 2; // 0-based: 0,1,2,3...
            return (doorNumber % 2 == 0) ? CellType.DOOR_SCARER : CellType.DOOR_LAUGHER;
        }

        return CellType.NORMAL;
    }

    public static boolean isDoor(int index) {
        CellType t = typeOf(index);
        return t == CellType.DOOR_SCARER || t == CellType.DOOR_LAUGHER;
    }
}