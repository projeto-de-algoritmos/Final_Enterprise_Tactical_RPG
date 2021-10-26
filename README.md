# Enterprise Tactical RPG Final

**Número da Lista**: 25<br>
**Conteúdo da Disciplina**: Final<br>

## Alunos

| Matrícula | Aluno |
| ---------- | -- |
| 15/0058462 |  Davi Antônio da Silva Santos |
| 18/0100840 |  Gabriel Azevedo Batalha |

## Sobre 
Um jogo cujo objetivo é sobreviver o maior número de turnos. Uma evolução da
entrega de Programação Dinâmica. O jogador deve controlar as suas duas unidades
e sobreviver o maior número de rodadas possível enquanto é perseguido pelas
unidades inimigas que utilizam os algoritmos estudados na disciplina: Dijkstra,
Quickselect (versão simplificada do mediana das medianas), e o algoritmo da
mochila com itens divisíveis ou indivisíveis.

## Screenshots
![Menu](https://i.imgur.com/dpGuh0z.png)
Menu

![Turno do jogador (mapa 20x20)](https://i.imgur.com/zhl2Ifb.png)
Turno do jogador (mapa 20x20)

![Turno do jogador (mapa 17x17)](https://i.imgur.com/KYCeI2Q.png)
Turno do jogador, com uma das unidades já movida (mapa 17x17)

![Turno do inimigo (mapa 23x23)](https://i.imgur.com/sUCAz1K.png)
Turno do inimigo (mapa 23x23)

## Instalação 
**Linguagem**: Java<br>
**Framework**: Swing<br>

### Requisitos

- Java JRE 11 ou superior.
  - JDK 11 ou superior exigido para compilar ou desenvolver
- Computador com *mouse*.
- Computador com dispositivo reprodutor de áudio

Clone o repositório para compilar o projeto ou baixe somente o `.jar` e os
assets ou um *tarball* comprimido disponíveis nas
[releases](https://github.com/projeto-de-algoritmos/Final_Enterprise_Tactical_RPG/releases)

Para executar o programa, use
```
java -jar EnterpriseTacticalRPG.jar
```

Caso opte por utilizar o `jar`, é preciso executá-lo no mesmo diretório que o
diretório `assets`. O *tarball* comprimido já possui o `jar` na estrutura de
diretórios correta.

O jogo é controlado pelo *mouse*. Há a possibilidade de escolha do tamanho do
mapa até no máximo 30x30 e no mínimo 16x16 posições. também é possível escolher
se deseja ver as animações dos inimigos e se deseja escutar sons para cada uma
das ações realizadas.

O jogador é representado pelos pontos azuis na tela e deve fugir dos pontos
vermelhos inimigos.
Os quadrados vermelhos são inimigos que seguem o jogador independentemente,
traçando o caminho de menor custo usando o algoritmo de Dijkstra.

Já os círculos vermelhos são um exército que persegue o jogador usando um
algoritmo ganancioso. O exército ganancioso tem um número fixo de casas que
pode movimentar, e escolhe o inimigo que chega mais próximo do jogador no menor
número de casas.

Os semicírculos vermelhos são um exército que persegue o jogador escolhendo
sempre a unidade na mediana dos custos para chegar até o jogador. Esses
inimigos seguem a mesma limitação de custo de movimento por unidade presente
nos inimigos comuns.

Os "quartos de círculos" são um exército que persegue o jogador usando o
algoritmo do agendamento de intervalos com pesos. Cada caminho até o alvo, no
caso o jogador, representa um evento com início e fim, sendo este último
determinado pelo número de rodadas que o inimigo analisado demoraria para
alcançar o alvo. O peso de cada tarefa é a distância em casas que será
percorrida até o alvo, facilitando para o jogador.

De acordo com o algoritmo, as tarefas incompatíveis de menor valor tendem a não
ser escolhidas, por isso é comum ver dois inimigos não se movendo. Nesse caso,
eles alcançam o jogador ao mesmo tempo e possuem menor valor para o algoritmo.

As áreas são coloridas conforme os custos para atravessá-las. Regiões verdes
possuem o custo mais baixo, e quanto mais amarelo, mais alto o custo. Regiões
pretas são intransponíveis.

Todos os exércitos inimigos que perseguem o jogador podem cair em situações de
empate, em que uma unidade pode perseguir igualmente qualquer uma das unidades
controladas pelo jogador. Nessas situações, há um algoritmo de desempate, que
seleciona o caminho de menor custo possível para cada unidade.

A partida termina quando todas as unidades do jogador são alcançadas por
qualquer um dos inimigos, ou quando não possuem movimentos válidos restantes.

## Desenvolvimento

Ao importar o projeto em sua IDE talvez seja necessário retirar o `.jar` gerado
do caminho do projeto. É possível que a IDE tente usar as classes empacotadas
no lugar das que estão definidas no código fonte.
