#include <iostream>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
using namespace std;
int main(int args, char *argv[]) {
    string line;
    int file = open(argv[1], O_RDWR | O_APPEND);
    getline(std::cin, line);
    while (line != "exit()") {
        write(file, line.c_str(), line.length());
        write(file, "\n", 1);
        getline(std::cin, line);
    }
    return 0;
}
