#ifndef MINES_UI_H
#define MINES_UI_H

#include "game.h"

void read_player_name(Game *game);
void play_game(Game *game);
bool repeat_game(bool is_game_repeat);
bool enter_letter();
int *get_parameters();
int *read_parameters_of_game();

#endif // MINES_UI_H