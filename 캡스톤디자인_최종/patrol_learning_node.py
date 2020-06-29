import pandas as pd
import random
import numpy as np


def rargmax(vector):
    m = np.amax(vector)
    indices = np.nonzero(vector == m)[0]
    return random.choice(indices)


class env():
    def __init__(self):
        self.state_list = []
        self.action_list = []
        self.reward_list1 = []
        self.reward_list2 = []
        self.reward_list3 = []
        self.reward_list4 = []
        self.env_create()
        self.create_reward()
        self.curr_state = 10
        self.epilson = 0.99
        self.decay_epilson = 0.9995
        self.Q = np.zeros([4, 310, 6])
        self.lr = 0.001
        self.y = 0.9
        self.path = []
        self.goal_num = 0

    def env_create(self):
        node_data = pd.read_csv('중구_nodes_200331_1.csv', encoding='cp949')
        link_data = pd.read_csv('중구_links_200331_1.csv', encoding='cp949')
        node_data = node_data.drop(node_data.columns[[0]], axis='columns')
        node_data = node_data.drop(node_data.columns[[1, 2, 3, 4]], axis='columns')
        link_data = link_data.drop(link_data.columns[[0, 1]], axis='columns')
        link_data = link_data.drop(link_data.columns[[2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]], axis='columns')
        node = node_data.values
        link = link_data.values
        link_num = 0
        action_list = [[0 for j in range(6)] for i in range(310)]
        for i in range(310):
            for j in range(6):
                if link[link_num][0] == i:
                    action_list[i][j] = link[link_num][1]
                    link_num = link_num + 1
                    if link_num >= len(link):
                        break
                else:
                    action_list[i][j] = -100
        for k in range(1, 6, 1):
            action_list[309][k] = -100
        state_list = [[0 for j in range(3)] for i in range(310)]
        for i in range(310):
            state_list[i][1] = node[i][1]
            state_list[i][2] = node[i][2]
        self.state_list = state_list
        self.action_list = action_list

    def create_reward(self):
        self.reward_list1 = [0 for i in range(310)]
        self.reward_list2 = [0 for i in range(310)]
        self.reward_list3 = [0 for i in range(310)]
        self.reward_list4 = [0 for i in range(310)]
        # self.reward_list1[226]=100
        self.reward_list1[308] = 100
        self.reward_list2[306] = 100
        self.reward_list3[141] = 100
        self.reward_list4[10] = 100

    def act(self, action):
        done = False
        next_state = self.action_list[self.curr_state][action]
        if next_state == -100:
            next_state = self.curr_state
        self.curr_state = next_state
        if self.goal_num == 0:
            reward = self.reward_list1[next_state]
        elif self.goal_num == 1:
            reward = self.reward_list2[next_state]
        elif self.goal_num == 2:
            reward = self.reward_list3[next_state]
        elif self.goal_num == 3:
            reward = self.reward_list4[next_state]
        if reward >= 1:
            if self.goal_num != 3:
                self.goal_num = self.goal_num + 1
            else:
                done = True
        return next_state, reward, done

    def reset(self):
        self.curr_state = 10
        self.goal_num = 0

    def qlearning(self):
        for i in range(5000):
            self.reset()
            s = 10
            rAll = 0
            reward = 0
            tp = 0
            print("num_episdoe", i, self.epilson)
            times = 15000
            done = False
            for time in range(times):
                if time == 14999:
                    print("fail")
                if self.epilson >= random.random():
                    a = np.random.randint(0, 6)
                else:
                    a = rargmax(self.Q[tp, s, :])  # + np.random.randn(1,4)*(20))
                s1, reward, done = self.act(a)
                # print(s,s1,reward)
                # s1=s1[1]
                self.Q[tp, s, a] = self.Q[tp, s, a] + self.lr * (
                            reward + self.y * np.max(self.Q[tp, s1, :]) - self.Q[tp, s, a])
                # s,a]= timeW*Q1[s,a] + treasureW*Q2[s,a]
                if reward >= 1:
                    print(s1, reward, time)
                    if tp != 3:
                        tp = tp + 1
                    else:
                        break
                s = s1
            if self.epilson > 0.01:
                self.epilson = self.epilson * self.decay_epilson
        print("train stop")
        self.reset()
        s = 10
        tp1 = 0
        final_step = 0
        while (True):
            final_step = final_step + 1
            self.path.append([self.state_list[s][1], self.state_list[s][2]])
            a = rargmax(self.Q[tp1, s, :])
            s1, reward, done = self.act(a)
            if reward >= 1:
                print(s1, reward, done)
                if tp1 != 3:
                    tp1 = tp1 + 1
                else:
                    break
            s = s1


patrol_env = env()
print(patrol_env.state_list)
print(patrol_env.action_list)


patrol_env.qlearning()


patrol_env.path.append([37.5648325, 126.98996480000001])
print(patrol_env.path)
print(len(patrol_env.path))
print(patrol_env.state_list[308][1], patrol_env.state_list[308][2])
print(patrol_env.state_list[306][1], patrol_env.state_list[306][2])
print(patrol_env.state_list[141][1], patrol_env.state_list[141][2])


import folium

center = [37.5648325, 126.9899648]
m = folium.Map(location=center, zoom_start=15)
folium.PolyLine(locations=patrol_env.path,
                tooltip='PolyLine').add_to(m)
m
