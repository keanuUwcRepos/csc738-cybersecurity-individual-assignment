
# CSC738-cybersecurity-assignment
# Task 2

## Contents <
[Introduction](#introduction)

[Original ciphertext](#original-ciphertext)

[Why is this useful](#why-is-this-useful)

## Introduction
[Go to Top](#top)

The following provides in-sight to a ‘how to’ approach when required to ‘break’  a simple substitution cipher, without the need of the cryptographic key, to display the ciphertexts original corresponding plaintext message. This is a very basic cryptanalysis method used against  simple substitution, or mono-alphabetic substitution ciphers.

For the purposes of this explanation, the following ciphertext will be analyzed and broken…

[](#top)

## Original ciphertext
[Go to Top](#top)

![/Users/keanujohnston/Documents/Development/Repos/csc738 cybersecurity/csc738-cybersecurity-individual-assignment/images/Image7.png](Image7.png)

# 

Firstly, the reason this cryptanalysis method is referred to as frequency cryptanalysis, is because we are reading statistical data and incorporating/manipulating such data to our ciphertext in order to make valid English words (in this case, we are analyzing the English alphabet). Take a look below:

  - Image1.png: CAMS introductory marker, at the entrance of the CAMS building.
    
![/Users/keanujohnston/Documents/Development/Repos/csc738 cybersecurity/csc738-cybersecurity-individual-assignment/images/Image1.png](Image1.png)

The data set above correlates to the percentage frequency of the most occurring letter in the English vocabulary. As you can see, “E” is the most occurring letter which exists in >12% of all English words.

# Why is this useful

Based on our ciphertext, once performing the same frequency analysis we performed on the English alphabet, we obtain the following data:

![/Users/keanujohnston/Documents/Development/Repos/csc738 cybersecurity/csc738-cybersecurity-individual-assignment/images/Image2.png](Image2.png)

Therefore, comparing this data to the data representing the English alphabet, we are able to distinguish some substitution decisions. For a more visual representation, these two data sets have been represented in graphs below:

![/Users/keanujohnston/Documents/Development/Repos/csc738 cybersecurity/csc738-cybersecurity-individual-assignment/images/Image3.png](Image3.png)

![/Users/keanujohnston/Documents/Development/Repos/csc738 cybersecurity/csc738-cybersecurity-individual-assignment/images/Image4.png](Image4.png)

Again, as we can see the most common occurrence in the English alphabet is “E” followed by “T”, and in our ciphertext it’s “S” followed by “O”. Therefore we can replace all “S” letters in the ciphertext to “E” and likewise with “O” to “T”.

The first four words of our ciphertext now looks like this:

![/Users/keanujohnston/Documents/Development/Repos/csc738 cybersecurity/csc738-cybersecurity-individual-assignment/images/Image5.png](Image5.png)

Now looking at “tG” we know that the only potential valid English word that could come frome this would be to replace the “G” with an “o”, making the word “to”. There are multiple “tG” phrases in the now new/manipulated ciphertext which validates this assumption. As we continue to spot potential words, and continue correlating the letter frequencies, we end up getting more words, and we are able to make more educated assumptions.

Eventually, you will successfully transcribe the entire ciphertext to a readable plaintext format, which will look exactly like this:

![/Users/keanujohnston/Documents/Development/Repos/csc738 cybersecurity/csc738-cybersecurity-individual-assignment/images/Image6.png](Image6.png)

Below is a list depicting the plaintext (English Alphabet) characters that replaced all the ciphertext characters to solve.

![/Users/keanujohnston/Documents/Development/Repos/csc738 cybersecurity/csc738-cybersecurity-individual-assignment/images/Image8.png](Image8.png)

There you go, now that was fairly basic wasn’t it? The next explanation will be something a little more challenging that includes a bit of mathematical equations.

[Go to Top](#top)
