import pygame
from time import sleep
import model
import environment
import patrol_learning_grid

patrol_env = environment.env()
f = open("./data/좌표1.txt", 'r')
lines = f.readlines()
f.close()
game = [[0 for j in range(50)] for i in range(50)]
temp_game = []
for line in lines:
    temp_game.append(list(map(int, line.split())))
k = 0
for i in range(49, 1, -1):
    for j in range(50):
        if j in temp_game[i]:
            game[k][j] = -1
    k = k + 1
n = 50  # represents no. of side squares(n*n total squares)
scrx = n * 15
scry = n * 15
background = (51, 51, 51)  # used to clear screen while rendering
screen = pygame.display.set_mode((scrx, scry))  # creating a screen using Pygame
colors = [(51, 51, 51) for i in range(n ** 2)]
reward = patrol_env.goal_state
terminals = []
path = list(patrol_learning_grid.main())

for i in range(len(game)):
    for j in range(len(game)):
        if game[i][j] == 0:
            colors[n * i + j] = (255, 255, 255)
            terminals.append(n * i + j)
for i in range(len(reward)):
    [x, y] = reward[i]
    print(x, y)
    colors[n * x + y] = (255, 0, 255)
    terminals.append(n * x + y)


def layout(k):
    c = 0
    current_pos = path[k]
    for i in range(0, scrx, 15):
        for j in range(0, scry, 15):
            pygame.draw.rect(screen, (255, 255, 255), (j, i, j + 15, i + 15), 0)
            pygame.draw.rect(screen, colors[c], (j + 3, i + 3, j + 19, i + 19), 0)
            c += 1
            pygame.draw.circle(screen, (25, 129, 230), (current_pos[1] * 15 + 10, current_pos[0] * 15 + 10), 6, 0)


run = True
k = 0
while run:
    sleep(1)
    screen.fill(background)
    if k < len(path):
        layout(k)
    else:
        pygame.quit()
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            run = False
    pygame.display.flip()
    # episode()
    k += 1
pygame.quit()
