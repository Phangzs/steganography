# Steganography

Allows low-quality images to be hidden within higher-quality images. The least significant bits of image are changed to the most significant bits of hidden images. Can be tweaked to allow for higher-quality images to be hidden, at the cost of the quality of the image mask, which may make it obvious that an image is being encoded in the image mask.

Steganography.java contains most of the logic. Changes to the variable names that hold the values of the path to the images to be encoded and the mask can be changed to mask any image (with any other image), provided the mask and image to be hidden can both be accessed by the program, meaning they both need to be local.

A project for a Computer Science class.
