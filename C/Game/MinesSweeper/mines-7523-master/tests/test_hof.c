#include "greatest.h"
#include "../hof.h"

TEST load_file_number_of_players() {
    char hof_file[] = "tests/score/optimal_size";
    Player list_of_players[PLAYERS_LIMIT];

    ASSERT_EQ(5, read_scores(hof_file, list_of_players));
    PASS();
}

TEST load_file_over_limit() {
    char hof_file[] = "tests/score/over_size";
    int high_size_of_list = PLAYERS_LIMIT + 5;
    Player list_of_players[high_size_of_list];

    ASSERT_EQ(PLAYERS_LIMIT, read_scores(hof_file, list_of_players));
    PASS();
}

TEST load_nonexistent_file() {
    char hof_file[] = "tests/score/nonexistent";
    Player list_of_players[PLAYERS_LIMIT];
    ASSERT_EQ(0, read_scores(hof_file, list_of_players));
    PASS();
}

TEST compare_loaded_and_saved_players() {
    Player list_of_players[PLAYERS_LIMIT] = {
            {"peter", 99},
            {"jozo",  55},
            {"alena", 43},
    };
    int size_of_input_list = 3;
    char hof_file[] = "tests/score/for_saving";

    save_scores(hof_file, list_of_players, size_of_input_list);
    Player test_list_of_players[size_of_input_list];
    int size_of_test_list =
            read_scores(hof_file, test_list_of_players);

            ASSERT_EQ(size_of_input_list, size_of_test_list);
    for (int i = 0; i < size_of_test_list; i++) {
        ASSERT_EQ(list_of_players[i].score, test_list_of_players[i].score);
        ASSERT_STR_EQ(list_of_players[i].name, test_list_of_players[i].name);
    }
    remove(hof_file);
    PASS();
}

TEST save_player_over_limit() {
    Player list_of_players[PLAYERS_LIMIT] = {
            {"peter", 99},
            {"jozo",  55},
            {"alena", 43},
            {"roman", 32},
            {"lubo",  21},
            {"milan", 20},
            {"alex",  18},
            {"dano",  9},
            {"kiko",  4},
            {"lubo",  2}
    };
    int size = PLAYERS_LIMIT + 1;
    char hof_file[] = "tests/score/for_saving";
    Player *player = (Player *) calloc(1, sizeof(Player));
    strcpy(player->name, "lucia");
    player->score = 26;

    ASSERT_FALSE(add_player_to_list(list_of_players, &size, *player));
    free(player);
    remove(hof_file);
    PASS();
}

TEST add_player_to_optimal_list() {
    Player list_of_players[PLAYERS_LIMIT] = {
            {"peter", 99},
            {"alex",  18},
            {"lubo",  2}
    };
    int size = 3;
    Player *player = (Player *) calloc(1, sizeof(Player));
    strcpy(player->name, "lucia");
    player->score = 26;

    int size_before_adding = size;
    add_player_to_list(list_of_players, &size, *player);
    ASSERT_EQ(size_before_adding + 1, size);
    free(player);
    PASS();
}


TEST add_player_to_list_if_the_same_result() {
    Player list_of_players[PLAYERS_LIMIT] = {
            {"alex",100},
            {"jack",72},
            {"jessy",64},
            {"jessy", 64}
    };
    int size = 4;
    Player *player = (Player *) calloc(1, sizeof(Player));
    strcpy(player->name, "lucia");
    player->score = 64;

    int size_before_adding = size;
    add_player_to_list(list_of_players, &size, *player);
    ASSERT_EQ(size_before_adding + 1, size);

    ASSERT_EQ(strcmp(list_of_players[2].name,"lucia"),0);
    ASSERT_EQ(64,list_of_players[2].score);

    ASSERT_EQ(strcmp(list_of_players[3].name,"jessy"),0);
    ASSERT_EQ(64,list_of_players[3].score);

    free(player);
    PASS();
}

//test if the list is fully populated
TEST add_player_to_filled_list() {
    Player list_of_players[PLAYERS_LIMIT] = {
            {"alex",100},
            {"jack",72},
            {"jessy",64},
            {"will",51},
            {"britney",50},
            {"peter",50},
            {"oliver",45},
            {"harry",30},
            {"thomas",21},
            {"oscar",2}
    };
    int size = 10;
    Player *player = (Player *) calloc(1, sizeof(Player));
    Player *player_1 = (Player *) calloc(1, sizeof(Player));

    strcpy(player->name, "charley");
    player->score = 26;
    int size_before_adding = size;
    add_player_to_list(list_of_players, &size, *player);
    ASSERT_EQ(size_before_adding,size);

    ASSERT_EQ(strcmp(list_of_players[8].name,"charley"),0);
    ASSERT_EQ(26,list_of_players[8].score);

    ASSERT_EQ(strcmp(list_of_players[9].name,"thomas"),0);
    ASSERT_EQ(21,list_of_players[9].score);

    strcpy(player_1->name, "jacob");
    player_1->score = 1;
    size = 10;
    add_player_to_list(list_of_players, &size, *player_1);
    ASSERT_EQ(size_before_adding,size);

    ASSERT_EQ(strcmp(list_of_players[8].name,"charley"),0);
    ASSERT_EQ(26,list_of_players[8].score);

    ASSERT_EQ(strcmp(list_of_players[9].name,"thomas"),0);
    ASSERT_EQ(21,list_of_players[9].score);

    free(player);
    free(player_1);
    PASS();
}

SUITE (test_hall_of_fame) {
    RUN_TEST(load_file_number_of_players);
    RUN_TEST(load_file_over_limit);
    RUN_TEST(load_nonexistent_file);
    RUN_TEST(compare_loaded_and_saved_players);
    RUN_TEST(save_player_over_limit);
    RUN_TEST(add_player_to_optimal_list);
    RUN_TEST(add_player_to_list_if_the_same_result);
    RUN_TEST(add_player_to_filled_list);
}
