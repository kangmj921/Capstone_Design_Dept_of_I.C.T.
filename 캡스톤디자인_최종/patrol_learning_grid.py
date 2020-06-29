import astar
import environment
import model
import numpy as np


def main():
    patrol_env = environment.env()
    temp = astar.maze
    dqn = model.Dqn_network()

    # reward_adjust constant
    minus_reward = []
    init = [0, 3]
    for i in range(7):
        result = []
        goal = temp.goal_state[i]
        astar_Solver = astar.Aster_Solver(temp, init, goal, display=False)
        result = result + astar_Solver.solve_maze()
        reward = temp.game_original[temp.goal_state[i][0]][temp.goal_state[i][1]]
        reward = np.round(len(result) / reward, 2)
        minus_reward.append(reward)
    patrol_env.minus_reward = minus_reward

    # adjust_reward
    episode = 500
    dqn.epsilon = 1.0
    for e in range(episode):
        patrol_env.reset()
        step = 0
        state = [0, 0]
        tp = 0
        score = 0
        ret = 4
        while (True):
            step = step + 1
            action = dqn.choose_action(state)
            next_state, reward, done = patrol_env.act(action)
            dqn.remember_memory(state, action, reward, next_state, done)
            # print(state[0],maze.states[state[1]][1],maze.states[state[1]][2],next_state[0],maze.states[next_state[1]][1],maze.states[next_state[1]][2],reward,done)
            # print(maze.curr_state[0],maze.states[maze.curr_state[1]][1],maze.states[maze.curr_state[1]][2])
            state = next_state
            if reward >= 1:
                score = score + reward
                tp = tp + 1
                if state[0] != 3:
                    state[0] = state[0] + 1
                if e % 100 == 0:
                    print(reward, done, step)
            if step > 20000:
                if e % 100 == 0:
                    print(state[0], patrol_env.states[state[1]][1], patrol_env.states[state[1]][2], next_state[0],
                          patrol_env.states[next_state[1]][1], patrol_env.states[next_state[1]][2], reward, done)
                # retry=int(score//ret)
                dqn.replay_experience()
                if e % 100 == 0:
                    print("fail")
                break

                # dqn.update_target_model()
            if done:
                patrol_env.adjust_reward()
                # retry = int(score//4)
                dqn.replay_experience()
                if dqn.epsilon > dqn.e_min:
                    dqn.epsilon *= dqn.e_decay
                if e % 100 == 0:
                    print("episode: {}/{},  e: {:.2} \t @ {}"
                          .format(e, episode, dqn.epsilon, step))
                # dqn.replay_experience()
                dqn.update_target_model()
                break

    # dqn or qlearning(faster)
    Q_model = model.Q_learning()
    Q_model.env = patrol_env
    Q_model.learn()
    Q_model.learn_back()
    print(Q_model.path)
    return Q_model.path
