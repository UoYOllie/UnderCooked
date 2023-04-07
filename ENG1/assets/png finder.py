import os

def find_png(directory):

    list_of_pngs = []

    current_directory = os.listdir(directory)
    if directory == "./":
        directory = "."


    for file in current_directory:
        if (".png" in file) or (".atlas" in file):
            list_of_pngs.append((directory + "/" + file)[2:])

    print(current_directory)

    for file in current_directory:
        if ("." not in file):
            print("going to:", directory + "/" + file)
            new_list = find_png(directory + "/" + file)

            for a in new_list:
                list_of_pngs.append(a)
            

    return list_of_pngs


output_list = find_png("./")

output = open("assets.txt", "w")

for asset in output_list:
    output.write(asset + "\n")

output.close()
