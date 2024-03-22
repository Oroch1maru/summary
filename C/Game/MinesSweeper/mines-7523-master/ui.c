#include <stdio.h>
#include <string.h>
#include <assert.h>
#include <stdlib.h>
#include <math.h>

#include "ui.h"
#include "view.h"
#include "str_utils.h"

#define HOF_FILENAME "score.txt"
#define MIN_SIZE 2
#define MAX_SIZE 30

/* private functions */
void game_loop(Game *game);
void print_play_field(Game *game, int input_row, int input_column);
void print_score(Player *list_of_players, int number_of_all_players);
void print_input_rules();
void print_input_marks(int amount_of_marks);

/**
 * Ask a player for his name and store in the game.
 */
void read_player_name(Game *game) {
    assert(game != NULL);
    printf("Zadajte vaše meno prosím: \n");
    char name_of_player[MAX_PLAYER_LENGTH];
    fgets(name_of_player, MAX_PLAYER_LENGTH, stdin);
    str_remove_spaces(name_of_player);
    strcpy(game->player->name, name_of_player);
}

/**
 * Ask a player for parameters of the game.
 */
int* read_parameters_of_game() {
    const int EASY_DIFFICULTY = 1;
    const int MEDIUM_DIFFICULTY = 2;
    const int HARD_DIFFICULTY = 3;

    //Allocate an array of three integers: number of rows, colums and mines
    int* parameters = calloc(3, sizeof(int*));

    //Addition array
    int values_for_check[3]; 

    // Get user input and validate it
    char input[256];
    fgets(input, sizeof(input), stdin);
    if(sscanf(input, "%d %d %d", &values_for_check[0], &values_for_check[1], &values_for_check[2]) == 3 &&
        values_for_check[0] >= MIN_SIZE && values_for_check[1] >= MIN_SIZE && 
        values_for_check[0] <= MAX_SIZE && values_for_check[1] <= MAX_SIZE && 
        (values_for_check[2] == EASY_DIFFICULTY || values_for_check[2] == MEDIUM_DIFFICULTY || 
        values_for_check[2] == HARD_DIFFICULTY))
    {
        parameters[0] = values_for_check[0];
        parameters[1] = values_for_check[1];
        parameters[2] = values_for_check[2];
    }
    else{
        free(parameters);
        return NULL;
    }    
    
    /* Calculations for the number of mines. For the easy level, 20% of the number of cells,
       for the medium level 35%, and for the difficult level 50%.*/
    if(parameters[2] == EASY_DIFFICULTY){
        parameters[2] = (int)round(parameters[0] * parameters[1] * 0.2);
    }else if(parameters[2] == MEDIUM_DIFFICULTY){
        parameters[2] = (int)round(parameters[0] * parameters[1] * 0.35);
    }
    else parameters[2] = (int)round(parameters[0] * parameters[1] * 0.5);

    return parameters;
} 


/**
 * Handles the process of entering game parameters
 * Loops until the data is correct
 */
int* get_parameters(){
    // Prompt user for input
    printf("\nZadajte počet riadkov a stĺpcov a oddelených medzerou.\n");
    printf("Minimálna hodnota pre obe polia je %d, maximálna %d.\n", MIN_SIZE, MAX_SIZE);
    printf("Potom zadajte úroveň obtiažnosti - 1 pre ľahkú, 2 pre strednú a 3 pre ťažkú\n");
    printf("Zadajte hodnotu oddelenú medzerou, napríklad \"10 10 2\".\n");
    
    int* parameters = read_parameters_of_game();

    printf("\n");
    while(parameters == NULL){
        printf("Nesprávne zadávanie údajov. Zadajte ho znova: ");
        parameters = read_parameters_of_game();
        printf("\n");
    }
    return parameters;
}


/**
 * Handle whole process of the Game
 */
void play_game(Game *game) {
    assert(game != NULL);

    Player list_of_players[PLAYERS_LIMIT];
    int size_of_list = read_scores(HOF_FILENAME, list_of_players);
    if (size_of_list > 0) {
        print_score(list_of_players, size_of_list);
    }
    game_loop(game);

    if (game->game_state == SOLVED) {
        printf("Gratulujem %s. Riešenie je správne!\n", game->player->name);
    }
    else {
        printf("Ľutujem %s. Riešenie je nesprávne!\n", game->player->name);
    }
    printf("Vaše skóre je: %d\n", game->player->score);

    bool is_player_added_to_list =
        add_player_to_list(list_of_players, &size_of_list, *game->player);

    if (is_player_added_to_list) {
        save_scores(HOF_FILENAME, list_of_players, size_of_list);
    }
}

/**
 * Handles players input process
 * Loops until game state is playing
 */
void game_loop(Game *game) {
    assert(game != NULL);
    int input, input_row = -1, input_column = -1;
    char intput_action = 'o';

    do {
        print_play_field(game, input_row, input_column);
        print_input_rules();

        while ((input = scanf("%c %d %d", &intput_action, &input_row, &input_column )) == 0) {
            scanf("%*[^\n] %*[^\n]");
            print_input_rules();
        }
        if (input != EOF ) {
            // if input is not empty and is correct then open the tile
            if(intput_action == 'o'){
            open_tile(game, input_row - 1, input_column - 1);}
            else if(intput_action == 'm'){
               int total_mines = amount_all_mines(game);
               int amount_of_marks = amount_all_marks(game);
              if(amount_of_marks != total_mines || is_marked(game, input_row - 1, input_column - 1)){
            mark_tile(game, input_row - 1, input_column - 1);
                }else{
                   int amount_marks = amount_all_marks(game);
                  print_input_rules();
                  print_input_marks(amount_marks);  
                }
            }
        }

    } while (game->game_state == PLAYING);
    print_play_field(game, input_row, input_column);
}

void print_score(Player *list_of_players, int number_of_all_players) {
    char *text = view_hof(list_of_players, number_of_all_players);
    printf("%s", text);
    free(text);
}

void print_play_field(Game *game, int input_row, int input_column) {
    char *field = view_play_field(game->board, input_row, input_column);
    printf("\n%s\n", field);
    free(field);
}

void print_input_marks(int amount_of_marks) {
    printf("Máte obmedzený počet značiek: %d\n",amount_of_marks);
}


void print_input_rules() {
    printf("Zadajte otvoriť(o) alebo označiť(m) pole, číslo riadka, medzeru a číslo stĺpca. Napr. o 2 3, m 5 9.\n");
}

bool enter_letter() {
    char input;
    printf("Chcete hrať znova? (y/n)\n");
    scanf(" %c", &input);
    getchar();
    if (input == 'y') {
        return true;
    }
    return false;
}

bool repeat_game(bool is_game_repeat) {
    return is_game_repeat;
}