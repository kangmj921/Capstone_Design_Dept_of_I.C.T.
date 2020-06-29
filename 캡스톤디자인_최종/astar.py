import copy


class Node(object):
    def __init__(self, state, start_point, goal_point):
        self.state = state
        self.start_point = start_point
        self.goal_point = goal_point
        self.hs = (self.state[0] - self.goal_point[0]) ** 2 + (self.state[1] - self.goal_point[1]) ** 2
        self.fs = 0
        self.parent_node = None

    def confirm_goal(self):
        if self.goal_point == self.state:
            return True
        else:
            return False


class NodeList(list):
    def find_nodelist(self, state):
        node_list = [t for t in self if t.state == state]
        return node_list[0] if node_list != [] else None

    def remove_from_nodelist(self, node):
        del self[self.index(node)]


class Aster_Solver(object):
    def __init__(self, maze, start_point, goal_point, display=False):
        self.Field = maze
        self.start_point = start_point
        self.goal_point = goal_point
        self.open_list = NodeList()
        self.close_list = NodeList()
        self.steps = 0
        self.score = 0
        self.display = display

    def set_initial_node(self):
        node = Node(self.start_point, self.start_point, self.goal_point)
        node.start_point = self.start_point
        node.goal_point = self.goal_point
        return node

    def go_next(self, next_actions, node):
        node_gs = node.fs - node.hs
        for action in next_actions:
            open_list = self.open_list.find_nodelist(action)
            dist = (node.state[0] - action[0]) ** 2 + (node.state[1] - action[1]) ** 2
            if open_list:
                if open_list.fs > node_gs + open_list.hs + dist:
                    open_list.fs = node_gs + open_list.hs + dist
                    open_list.parent_node = node
            else:
                open_list = self.close_list.find_nodelist(action)
                if open_list:
                    if open_list.fs > node_gs + open_list.hs + dist:
                        open_list.fs = node_gs + open_list.hs + dist
                        open_list.parent_node = node
                        self.open_list.append(open_list)
                        self.close_list.remove_from_nodelist(open_list)
                else:
                    open_list = Node(action, self.start_point, self.goal_point)
                    open_list.fs = node_gs + open_list.hs + dist
                    open_list.parent_node = node
                    self.open_list.append(open_list)

    def solve_maze(self):
        path = []
        node = self.set_initial_node()
        node.fs = node.hs
        self.open_list.append(node)
        while True:
            node = min(self.open_list, key=lambda node: node.fs)
            path.append(node.state)
            # print ("current state:  {0}".format(node.state))
            reward, tf = self.Field.get_val(node.state, self.goal_point)
            self.score = self.score + reward
            # print("current step: {0} \t score: {1} \n".format(self.steps, self.score))
            self.steps += 1
            if tf:
                print("Sucess!")
                return path
                break

            self.open_list.remove_from_nodelist(node)
            self.close_list.append(node)
            next_actions = self.Field.next_actions(node.state)
            self.go_next(next_actions, node)


class env():
    def __init__(self):
        self.game_original = []
        self.game = []
        self.states = []
        self.game_create()
        self.create_states()
        self.curr_state = [0, 0]
        self.goal_num = 7
        self.goal_state = [[17, 3], [25, 11], [1, 12], [20, 21], [12, 36], [41, 32], [45, 13]]

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
        # path1 episode:300 e:0.99 step=10000

        # path4 easy exmaple
        self.game_original[17][3] = 7.0
        self.game_original[25][11] = 22.0
        self.game_original[1][12] = 45.0
        self.game_original[20][21] = 67.0
        self.game_original[12][36] = 229.0
        self.game_original[41][32] = 33.0
        self.game_original[45][13] = 101.0
        self.game = copy.deepcopy(self.game_original)

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

    def get_val(self, state, goal_state):
        x = state[0]
        y = state[1]
        if [x, y] == goal_state:
            return self.game[x][y], True
        else:
            return self.game[x][y], False

    # Defining actions
    def goUp(self, state):
        x = state[0]
        y = state[1]
        if x == 0:
            return None
        else:
            reward = self.game[x - 1][y]
            if reward == 0:
                return None
            else:
                st = [x - 1, y]
                return st

    def goDown(self, state):
        x = state[0]
        y = state[1]
        if x == 49:
            return None
        else:
            reward = self.game[x + 1][y]
            if reward == 0:
                return None
            else:
                st = [x + 1, y]
                return st

    def goLeft(self, state):
        x = state[0]
        y = state[1]
        if y == 0:
            return None
        else:
            reward = self.game[x][y - 1]
            if reward == 0:
                return None
            else:
                st = [x, y - 1]
                return st

    def goRight(self, state):
        x = state[0]
        y = state[1]
        if y == 49:
            return None
        else:
            reward = self.game[x][y + 1]
            if reward == 0:
                return None
            else:
                st = [x, y + 1]
                return st

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
        if reward > 1:
            x = self.states[next_state[1]][1]
            y = self.states[next_state[1]][2]
            self.game[x][y] = -1
            if self.curr_state[0] != self.goal_num - 1:
                self.curr_state[0] = self.curr_state[0] + 1
            else:
                done = True
        return next_state, reward, done

    def next_actions(self, state):
        moveables = []
        moveables.append(self.goUp(state))
        moveables.append(self.goDown(state))
        moveables.append(self.goLeft(state))
        moveables.append(self.goRight(state))
        while True:
            if None in moveables:
                moveables.remove(None)
            else:
                break
        if len(moveables) != 0:
            return moveables
        else:
            return None

    def reset(self):
        self.curr_state = [0, 0]
        self.game = copy.deepcopy(self.game_original)


maze = env()
