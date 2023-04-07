import os

def find_png(directory):

    list_of_pngs = []

    current_directory = os.listdir(directory)
    if directory == "./":
        directory = "."


    for file in current_directory: # Searches for assets in current directory
        if (".png" in file) or (".atlas" in file): # Which file extensions to look for.
            list_of_pngs.append((directory + "/" + file)[2:])


    for file in current_directory: # Searches through all the folders.
        if ("." not in file): # This script assumes any file without an extension (or ".") is a folder
            print("going to:", directory + "/" + file)
            new_list = find_png(directory + "/" + file) # Goes one folder deeper and starts again.

            for a in new_list:
                list_of_pngs.append(a) # appends all items from the deeper recursion to the current list of assets.
            

    return list_of_pngs # Returns list of assets in the current directory (inc. all in nested folders)


output_list = find_png("./")

output = open("assets.txt", "w")

for asset in output_list: # Writes list of assets to 'assets.txt'
    output.write(asset + "\n")

output.close()
