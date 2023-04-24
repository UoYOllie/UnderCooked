import os

"""
IMPORTANT THINGS TO NOTE:

- "assets.txt" will be completely overwritten whenever this script is run.
- This program formats directories with a "/" as a seperator - This may not work on all operating systems.
- This program assumes any file without a file extension (or without a "." in its name) is a folder - This may not always be true.


- The script currently searches for the following extensions (If you add more, please update this list):
    - .png
    - .atlas

"""

def find_png(directory):

    list_of_pngs = []

    current_directory = os.listdir(directory)
    if directory == "./":                                     # String manipulation to avoid an error later on
        directory = "."


    for file in current_directory:                            # Searches for assets in current directory
        if (".png" in file) or (".atlas" in file):            # Which file extensions to look for.
            list_of_pngs.append((directory + "/" + file)[2:])


    for file in current_directory:                            # Searches through all the folders.
        if ("." not in file):                                 # This script assumes any file without an extension (or ".") is a folder
            print("going to:", directory + "/" + file)
            new_list = find_png(directory + "/" + file)       # Goes one folder deeper and starts again.

            for a in new_list:
                list_of_pngs.append(a)                        # appends all items from the deeper recursion to the current list of assets.
            

    return list_of_pngs                                       # Returns list of assets in the current directory (inc. all in nested folders)


output_list = find_png("./")

output = open("assets.txt", "w")

for asset in output_list:                                     # Writes list of assets to 'assets.txt'
    output.write(asset + "\n")

output.close()
print("\nassets.txt generated, file closed")


input("\nYou can now close this window...")
