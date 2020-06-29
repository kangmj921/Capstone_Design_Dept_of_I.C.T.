import copy


class env:
    def __init__(self):
        self.reward_max = -1000
        self.game_original = []
        self.game_back = []
        self.game1 = []
        self.game2 = []
        self.game3 = []
        self.game4 = []
        self.states = []
        self.gamechoice = []
        self.game_visited = []
        self.game_goal_visited = []
        self.step_count = []
        self.goal_state = [[17, 3], [25, 11], [1, 12], [20, 21], [12, 36], [41, 32], [45, 13]]
        self.minus_reward = []
        self.game_create()
        self.create_states()
        self.curr_state = [0, 0]
        self.goal_num = 4

    def game_create(self):
        f = open("./data/좌표1.txt", 'r')
        lines = f.readlines()
        f.close()
        temp_game = []
        self.game_original = [[0 for j in range(50)] for i in range(50)]
        for line in lines:
            temp_game.append(list(map(int, line.split())))
        k = 0
        for i in range(49, 1, -1):
            for j in range(50):
                if j in temp_game[i]:
                    self.game_original[k][j] = -1
            k = k + 1
        '''
        #ex1
        self.game_original[7][11]=33.0
        self.game_original[12][29]=17.0
        self.game_original[36][0]=5.0
        self.game_original[20][21]=45.0
        self.game_original[22][36]=67.0
        self.game_original[41][32]=229.0
        self.game_original[45][17]=101.0
        '''
        # ex2
        self.game_back = copy.deepcopy(self.game_original)
        self.game_original[17][3] = 7.0
        self.game_original[25][11] = 22.0
        self.game_original[1][12] = 45.0
        self.game_original[20][21] = 67.0
        self.game_original[12][36] = 229.0
        self.game_original[41][32] = 33.0
        self.game_original[45][13] = 101.0
        self.game1 = copy.deepcopy(self.game_original)
        self.game2 = copy.deepcopy(self.game_original)
        self.game3 = copy.deepcopy(self.game_original)
        self.game4 = copy.deepcopy(self.game_original)
        self.game = copy.deepcopy(self.game_original)
        self.game_visited = [[0 for j in range(52)] for i in range(52)]
        self.gamechoice = [copy.deepcopy(self.game1), copy.deepcopy(self.game2), copy.deepcopy(self.game3),
                           copy.deepcopy(self.game4)]

    def create_states(self):
        # creating states
        k = 0
        for i in range(50):
            for j in range(50):
                if self.game[i][j] != 0:
                    self.states.append([k, i, j])
                    k = k + 1

    def re_state(self):
        x = self.curr_state[0]
        y = self.curr_state[1]
        a = [x, y]
        return a

        # Defining actions

    def goUp(self):
        x = self.states[self.curr_state[1]][1]
        y = self.states[self.curr_state[1]][2]
        if x == 0:
            return self.re_state(), -1
        else:
            reward = self.game[x - 1][y]
            if reward == 0:
                return self.re_state(), -0.01
            else:
                for e in self.states:
                    if e[1] == x - 1 and e[2] == y:
                        self.curr_state[1] = e[0]
                if reward > 0:
                    return self.re_state(), reward
                else:
                    if reward == -1:
                        return self.re_state(), 0
                    else:
                        return self.re_state(), reward

    def goDown(self):
        x = self.states[self.curr_state[1]][1]
        y = self.states[self.curr_state[1]][2]
        if x == 49:
            return self.re_state(), -1
        else:
            reward = self.game[x + 1][y]
            if reward == 0:
                return self.re_state(), -0.01
            else:
                for e in self.states:
                    if e[1] == x + 1 and e[2] == y:
                        self.curr_state[1] = e[0]

                if reward > 0:
                    return self.re_state(), reward
                else:
                    if reward == -1:
                        return self.re_state(), 0
                    else:
                        return self.re_state(), reward

    def goLeft(self):
        x = self.states[self.curr_state[1]][1]
        y = self.states[self.curr_state[1]][2]
        if y == 0:
            return self.re_state(), -1
        else:
            reward = self.game[x][y - 1]
            if reward == 0:
                return self.re_state(), -0.01
            else:
                for e in self.states:
                    if e[1] == x and e[2] == y - 1:
                        self.curr_state[1] = e[0]

                if reward > 0:
                    return self.re_state(), reward
                else:
                    if reward == -1:
                        return self.re_state(), 0
                    else:
                        return self.re_state(), reward

    def goRight(self):
        x = self.states[self.curr_state[1]][1]
        y = self.states[self.curr_state[1]][2]
        if y == 49:
            return self.re_state(), -1
        else:
            reward = self.game[x][y + 1]
            if reward == 0:
                return self.re_state(), -0.01
            else:
                for e in self.states:
                    if e[1] == x and e[2] == y + 1:
                        self.curr_state[1] = e[0]

                if reward > 0:
                    return self.re_state(), reward
                else:
                    if reward == -1:
                        return self.re_state(), 0
                    else:
                        return self.re_state(), reward

    def act(self, action):
        done = False
        if action == 0:
            next_state, reward = self.goRight()
        elif action == 1:
            next_state, reward = self.goLeft()
        elif action == 2:
            next_state, reward = self.goUp()
        elif action == 3:
            next_state, reward = self.goDown()
        x = self.states[next_state[1]][1]
        y = self.states[next_state[1]][2]
        self.game_visited[x][y] = 1
        if reward >= 1:
            self.calculate_step()
            self.game_goal_visited.append([x, y])
            if self.curr_state[0] != self.goal_num - 1:
                self.game = copy.deepcopy(self.gamechoice[self.curr_state[0] + 1])
            for i in self.game_goal_visited:
                self.game[i[0]][i[1]] = -1
            if self.curr_state[0] != self.goal_num - 1:
                self.curr_state[0] = self.curr_state[0] + 1
            else:
                done = True
        return next_state, reward, done

    def act_back(self, action):
        done = False
        if action == 0:
            next_state, reward = self.goRight()
        elif action == 1:
            next_state, reward = self.goLeft()
        elif action == 2:
            next_state, reward = self.goUp()
        elif action == 3:
            next_state, reward = self.goDown()
        if reward >= 1:
            x = self.states[next_state[1]][1]
            y = self.states[next_state[1]][2]
            done = True
        return next_state, reward, done

    def calculate_step(self):
        result = 0
        for i in self.game_visited:
            result += i.count(1)
        self.step_count.append(result)
        self.game_visited = [[0 for j in range(52)] for i in range(52)]

    def adjust_reward(self):
        fn_reward = 0
        reward_pan = []
        for i in self.game_goal_visited:
            fn_reward += self.game_original[i[0]][i[1]]
        for i in self.step_count:
            fn_reward -= i
        if fn_reward >= self.reward_max:
            self.reward_max = fn_reward
        else:
            k = 0
            for j in self.goal_state:
                penalty = self.minus_reward[k]
                k = k + 1
                if j != self.game_goal_visited[0]:
                    if self.game1[j[0]][j[1]] - penalty >= 1:
                        self.game1[j[0]][j[1]] -= penalty
                    else:
                        self.game1[j[0]][j[1]] = -1
            k = 0
            for j in self.goal_state:
                penalty = self.minus_reward[k]
                k = k + 1
                if j != self.game_goal_visited[1]:
                    if self.game2[j[0]][j[1]] - penalty >= 1:
                        self.game2[j[0]][j[1]] -= penalty
                    else:
                        self.game2[j[0]][j[1]] = -1
            k = 0
            for j in self.goal_state:
                penalty = self.minus_reward[k]
                k = k + 1
                if j != self.game_goal_visited[2]:
                    if self.game3[j[0]][j[1]] - penalty >= 1:
                        self.game3[j[0]][j[1]] -= penalty
                    else:
                        self.game3[j[0]][j[1]] = -1
            k = 0
            for j in self.goal_state:
                penalty = self.minus_reward[k]
                k = k + 1
                if j != self.game_goal_visited[3]:
                    if self.game4[j[0]][j[1]] - penalty >= 1:
                        self.game4[j[0]][j[1]] -= penalty
                    else:
                        self.game4[j[0]][j[1]] = -1
        self.game_goal_visited = []
        self.step_count = []

    def reset(self):
        self.curr_state = [0, 0]
        self.gamechoice = [copy.deepcopy(self.game1), copy.deepcopy(self.game2), copy.deepcopy(self.game3),
                           copy.deepcopy(self.game4)]
        self.game = copy.deepcopy(self.game1)
        self.game_goal_visited = []

    def reset2(self):
        self.curr_state = [0, 0]
        self.game = copy.deepcopy(self.game1)
