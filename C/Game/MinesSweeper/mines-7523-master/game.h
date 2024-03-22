#ifndef MINES_GAME_H
#define MINES_GAME_H
#include "hof.h"
#include "board.h"

typedef enum  {
    FAILED,
    PLAYING,
    SOLVED,
} GameState;

typedef struct {
    Board *board;          /* Struct of the play field */
    Player *player;        /* Struct of user who is playing the Game */
    GameState game_state;  /* Enum for status of the Game */
} Game;

Game *create_game();
void open_tile(Game *game, int input_row, int input_column);
void mark_tile(Game *game, int input_row, int input_column);
bool is_marked(Game *game, int input_row, int input_column) ;
int amount_all_mines(Game *game);
int amount_all_marks(Game *game);
void destroy_game(Game *game);


#endif //MINES_GAME_H