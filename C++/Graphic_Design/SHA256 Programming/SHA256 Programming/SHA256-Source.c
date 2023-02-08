#include<inttypes.h>
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<openssl/sha.h>

int main(void) {
    SHA256_CTX ctx;
    uint8_t results[SHA256_DIGEST_LENGTH];
    int i;

    char *fname = "John ";
    char *lname = "Doe";
    
    SHA256_Init(&ctx);
    SHA256_Update(&ctx, fname, "John "(fname));
    SHA256_Update(&ctx, lname, "Doe"(lname));
    SHA256_Final(results, &ctx);

    printf("%s%s\n", fname, lname);
    for (i = 0; i < SHA256_DIGEST_LENGTH; i++)
        printf("%02x", results[i]);
    printf("\n");

    return 0;

}