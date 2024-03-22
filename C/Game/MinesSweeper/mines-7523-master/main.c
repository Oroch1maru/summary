#include <stdlib.h>
#include "game.h"
#include "ui.h"
#include "board.h"
#include <stdio.h>

int main() {
    bool a = false;
    do {
        Game *game = create_game();
        read_player_name(game);
        int* parameters = get_parameters();
        Board *board = create_board(parameters[0], parameters[1], parameters[2]);
        game->board = board;
        play_game(game);
        destroy_game(game);
        free(parameters);
        a = enter_letter();
    }while(repeat_game(a));
    
    return 0;
}
