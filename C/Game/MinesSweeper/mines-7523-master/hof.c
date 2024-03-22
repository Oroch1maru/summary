#include "hof.h"
#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>
#include <assert.h>


int compalator(const void *p1,const void *p2){
    Player* one=(Player*) p1;
    Player* two=(Player*) p2;
    return (two->score - one->score );
    
}


/**
 * Load hall of fame from the file
 * @return the number of loaded entries or 0, if there was error in loading
 */
int read_scores(char *filename, Player *list_of_players) {
    assert(filename != NULL);
    assert(list_of_players != NULL);
    FILE *file = fopen(filename, "r");

    if (file == NULL) {
        return 0;
    }
    int index_of_player = 0;
    while (fscanf(file, "%s %d", list_of_players[index_of_player].name,
                  &list_of_players[index_of_player].score) != EOF
           && index_of_player < PLAYERS_LIMIT) {

        index_of_player++;
    }

    fclose(file);
    return index_of_player;
}


/**
 * Save the hall of fame array to the file
 */
void save_scores(char *filename, Player *list_of_players, const int size) {
    assert(filename != NULL);
    assert(list_of_players != NULL);
    FILE *file = fopen(filename, "w");

    if (file == NULL) { return; }
    for (int i = 0; i < size; i++) {
        if (i < PLAYERS_LIMIT) {
            fprintf(file, "%s %d\n", list_of_players[i].name, list_of_players[i].score);
        }
    }
    fclose(file);
}


/**
 * Add Player to the hall of fame array
 * @return true, if Player (entry) was added to the list, false otherwise
 */
bool add_player_to_list(Player list_of_players[], int *size_of_list, const Player player) {
    assert(list_of_players != NULL && size_of_list != NULL);
    if (*size_of_list <= PLAYERS_LIMIT){
        char mas[30];
        /**
         * if the list size is equal to the maximum list size(10)
            the new player is checked to see if he has scored the minimum score or if the list size 
         * else the list size is less than the maximum list size, the new player is added to the end of the list
        */
        if((int)*size_of_list==PLAYERS_LIMIT){
            if(player.score>=list_of_players[*size_of_list-1].score){
                strcpy(list_of_players[*size_of_list-1].name, player.name);
                list_of_players[*size_of_list-1].score=player.score;
            }
        }else{
            strcpy(list_of_players[*size_of_list].name, player.name);
            list_of_players[*size_of_list].score=player.score;
            *size_of_list=*size_of_list+1;
        }
        qsort(list_of_players, (size_t)*size_of_list, sizeof list_of_players[0], compalator);
        //check if a player on the list has the same score as the new player, the new player becomes higher on the list than the old player
        for (int i = (*size_of_list-1); i >=0; i--){
            if(i!=0 && strcmp(list_of_players[i].name,player.name)==0 && list_of_players[i].score==player.score && list_of_players[i-1].score==list_of_players[i].score){
                strcpy(mas, list_of_players[i].name);
                strcpy(list_of_players[i].name,list_of_players[i-1].name);
                strcpy(list_of_players[i-1].name, mas);
            }
        }
    }else{

        return false;
    }
    return true;
}

