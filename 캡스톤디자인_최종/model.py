import copy
import random
import environment
import numpy as np
from tabulate import tabulate
from collections import deque
from keras.models import Sequential
from keras.layers import Dense
from keras.optimizers import Adam
import pylab


def rargmax(vector):
    m = np.amax(vector)
    indices = np.nonzero(vector == m)[0]
    return random.choice(indices)


class Dqn_network():
    def __init__(self):
        self.lr = 0.01
        self.gamma = 0.9
        self.epsilon = 1.0
        self.e_decay = 0.9997
        self.e_min = 0.01
        self.action_num = 4
        self.memory_size = 20000
        # self.memory = Memory(self.memory_size)  -per
        self.memory = deque(maxlen=30000)
        self.batch_mini = 300
        self.model = self.build_model()
        self.target_model = self.build_model()
        # 타깃 모델 초기화
        self.update_target_model()

    def update_target_model(self):
        self.target_model.set_weights(self.model.get_weights())

    def remember_memory(self, state, action, reward, next_state, done):
        self.memory.append((state, action, reward, next_state, done))

    def build_model(self):
        model = Sequential()
        model.add(Dense(64, input_shape=(1840,), activation='relu', kernel_initializer='he_normal'))
        # model.add(Flatten())
        # model.add(Dense(32, activation='tanh'))
        model.add(Dense(64, activation='relu', kernel_initializer='he_normal'))
        model.add(Dense(4, activation='sigmoid', kernel_initializer='he_normal'))
        model.compile(loss="mse", optimizer=Adam(lr=self.lr))
        model.summary()
        return model

    def dqn_input(self, state):
        state_list = np.zeros(1840)
        index = state[0] * 460 + state[1]
        state_list[index] = 1
        state_list = state_list.reshape(1, 1840)
        return state_list

    def choose_action(self, state):
        if self.epsilon >= random.random():  # or  (self.train_step_counter < self.observe)):
            return np.random.randint(0, self.action_num)
        else:
            action_index = self.dqn_input(state)
            action_q = self.model.predict(action_index)
            action_index = rargmax(action_q[0])
            return action_index

    def replay_experience(self):
        batch_size = self.batch_mini
        minibatch = random.sample(self.memory, batch_size)
        actions, rewards, dones = [], [], []
        errors = np.zeros(batch_size)
        states = np.zeros((batch_size, 1840))
        next_states = np.zeros((batch_size, 1840))
        for i in range(batch_size):
            states[i] = self.dqn_input(minibatch[i][0])
            actions.append(minibatch[i][1])
            rewards.append(minibatch[i][2])
            next_states[i] = self.dqn_input(minibatch[i][3])
            dones.append(minibatch[i][4])

        target = self.model.predict(states)
        target_val = self.target_model.predict(next_states)
        # 벨만 최적 방정식을 이용한 업데이트 타깃
        for i in range(batch_size):
            if dones[i]:
                target[i][actions[i]] = rewards[i]
            else:
                target[i][actions[i]] = rewards[i] + self.gamma * (
                    np.amax(target_val[i]))

        self.model.fit(states, target, batch_size=batch_size,
                       epochs=1, verbose=0)


class Q_learning():
    def __init__(self):
        self.num_episodes = 5000
        self.epilson = 0.99
        self.decay_epilson = 0.9995
        self.Q = np.zeros([5, 460, 4])
        self.goal_num = 4
        self.lr = 0.001
        self.y = 0.9
        self.env = environment.env()
        self.path = []
        self.visit_state = []
        self.final_state = 0
        self.showmap = []

    def reset_back(self):
        self.env.curr_state = [4, self.final_state]
        self.env.game = copy.deepcopy(self.env.game_back)
        for j in self.visit_state:
            x = self.env.states[j][1]
            y = self.env.states[j][2]
            self.env.game[x][y] = -0.01
        self.env.game[self.env.states[self.final_state][1]][self.env.states[self.final_state][2]] = -1
        self.env.game[0][3] = 500

    def learn(self):
        scores = list()
        for i in range(self.num_episodes):
            # Reset environment and get first new observation
            self.env.reset()
            s = 0
            tp = 0
            score = 0
            reward = 0
            j = 0
            if i % 100 == 0:
                print("num_episdoe", i, self.epilson)
            times = 15000
            done = False
            # The Q-Table learning algorithm
            for time in range(times):
                # while True:
                if time == 14999 and i % 100 == 0:
                    print("fail")
                j = j + 1
                score -= 0.1
                # Choose an action by greedily (with noise) picking from Q table
                if self.epilson >= random.random():
                    a = np.random.randint(0, 4)
                else:
                    a = rargmax(self.Q[tp, s, :])  # + np.random.randn(1,4)*(20))
                s1, reward, done = self.env.act(a)
                s1 = s1[1]
                score += reward
                self.Q[tp, s, a] = self.Q[tp, s, a] + self.lr * (
                            reward + self.y * np.max(self.Q[tp, s1, :]) - self.Q[tp, s, a])
                # s,a]= timeW*Q1[s,a] + treasureW*Q2[s,a]
                if reward >= 1:
                    if i % 100 == 0:
                        print(self.env.states[s1][1], self.env.states[s1][2], reward)
                    tp = tp + 1
                if tp > 3:
                    if i % 100 == 0:
                        print("gameover", j)
                    break
                s = s1
            if self.epilson > 0.01:
                self.epilson = self.epilson * self.decay_epilson
            scores.append(score)
        episodes = [i for i in range(self.num_episodes)]
        pylab.plot(episodes, scores, 'b')
        pylab.title("Q_learning Score per Episode")
        pylab.xlabel("Episodes")
        pylab.ylabel("Score")
        #pylab.savefig("./Q_learning.png")
        print("train stop")
        self.env.reset()
        s = 0
        tp1 = 0
        final_step = 0
        self.showmap = copy.deepcopy(self.env.game_original)
        while True:
            final_step = final_step + 1
            self.showmap[self.env.states[s][1]][self.env.states[s][2]] = '@'
            self.path.append([self.env.states[s][1], self.env.states[s][2]])
            a = np.argmax(self.Q[tp1, s, :])
            s1, reward, done = self.env.act(a)
            s1 = s1[1]
            self.visit_state.append(s1)
            if reward >= 1:
                tp1 = tp1 + 1
                if final_step % 100 == 0:
                    print(self.env.states[s1][1], self.env.states[s1][2], reward, tp1)
            s = s1
            if tp1 >= 4:
                self.path.append([self.env.states[s1][1], self.env.states[s1][2]])
                self.final_state = s1
                if final_step % 100 == 0:
                    print(tabulate(self.showmap))
                    print("step", final_step)
                break

    def learn_back(self):
        print("back train start")
        self.epilson = 0.99
        self.decay_epilson = 0.99995
        for i in range(self.num_episodes):
            if i > 3000:
                self.decay_epilson = 0.9995
            # Reset environment and get first new observation
            self.reset_back()
            s = self.final_state
            if i == 0:
                print(tabulate(self.env.game))
                print(s)
            rAll = 0
            reward = 0
            step = 0
            tp = 4
            if i % 100 == 0:
                print("num_episdoe", i, self.epilson)
            times = 10000
            # The Q-Table learning algorithm
            for time in range(times):
                # while True:
                if time == 9999 and i % 100 == 0:
                    print("fail")
                step = step + 1
                if self.epilson >= random.random():
                    a = np.random.randint(0, 4)
                else:
                    a = rargmax(self.Q[tp, s, :])  # + np.random.randn(1,4)*(20))
                if a == 0:
                    s1, reward = self.env.goRight()
                elif a == 1:
                    s1, reward = self.env.goLeft()
                elif a == 2:
                    s1, reward = self.env.goUp()
                else:
                    s1, reward = self.env.goDown()
                s1 = s1[1]
                # print(self.states[s1][1],self.states[s1][2],reward,tp1)
                self.Q[tp, s, a] = self.Q[tp, s, a] + self.lr * (
                            reward + self.y * np.max(self.Q[tp, s1, :]) - self.Q[tp, s, a])
                if reward >= 1:
                    if i % 100 == 0:
                        print(self.env.states[s1][1], self.env.states[s1][2], reward)
                        print("game_over", time)
                    break
                s = s1
            if self.epilson > 0.1:
                self.epilson = self.epilson * self.decay_epilson
        print("back_train stop")
        self.reset_back()
        s = self.final_state
        self.epsilon = 0.01
        tp1 = 4
        step = 0
        while True:
            step = step + 1
            if step > 200:
                print("train fail")
                break
            self.showmap[self.env.states[s][1]][self.env.states[s][2]] = '@'
            self.path.append([self.env.states[s][1], self.env.states[s][2]])
            a = rargmax(self.Q[tp1, s, :])
            if a == 0:
                s1, reward = self.env.goRight()
            if a == 1:
                s1, reward = self.env.goLeft()
            if a == 2:
                s1, reward = self.env.goUp()
            if a == 3:
                s1, reward = self.env.goDown()
            s1 = s1[1]
            print(self.env.states[s1][1], self.env.states[s1][2], reward, tp1)
            if reward >= 1:
                print(self.env.states[s1][1], self.env.states[s1][2], reward, tp1)
                print(tabulate(self.showmap))
                break
            s = s1
