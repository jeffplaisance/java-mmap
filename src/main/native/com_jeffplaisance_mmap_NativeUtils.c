#include "com_jeffplaisance_mmap_NativeUtils.h"
#include <sys/mman.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

JNIEXPORT jlong JNICALL Java_com_jeffplaisance_mmap_NativeUtils_mmap0(JNIEnv* env, jclass class, jlong addr, jlong length, jint java_prot, jint java_flags, jint fd, jlong offset) {
    int prot = 0;
    int flags = 0;
    if (java_prot & com_jeffplaisance_mmap_NativeUtils_PROT_EXEC) {
        prot |= PROT_EXEC;
    }
    if (java_prot & com_jeffplaisance_mmap_NativeUtils_PROT_READ) {
        prot |= PROT_READ;
    }
    if (java_prot & com_jeffplaisance_mmap_NativeUtils_PROT_WRITE) {
        prot |= PROT_WRITE;
    }
    if (java_prot & com_jeffplaisance_mmap_NativeUtils_PROT_NONE) {
        prot |= PROT_NONE;
    }

    if (java_flags & com_jeffplaisance_mmap_NativeUtils_MAP_SHARED) {
        flags |= MAP_SHARED;
    }
    if (java_flags & com_jeffplaisance_mmap_NativeUtils_MAP_PRIVATE) {
        flags |= MAP_PRIVATE;
    }
    if (java_flags & com_jeffplaisance_mmap_NativeUtils_MAP_ANON) {
        flags |= MAP_ANON;
    }

    void* ret = mmap((void*)addr, length, prot, flags, fd, offset);
    if (ret == MAP_FAILED) {
        return com_jeffplaisance_mmap_NativeUtils_MAP_FAILED;
    }
    return (jlong) ret;
}

JNIEXPORT jint JNICALL Java_com_jeffplaisance_mmap_NativeUtils_munmap0(JNIEnv* env, jclass class, jlong addr, jlong length) {
    return munmap((void*)addr, length);
}

JNIEXPORT jint JNICALL Java_com_jeffplaisance_mmap_NativeUtils_open(JNIEnv* env, jclass class, jstring path, jint java_flags, jint mode) {
    int flags = 0;
    int fd;
    const char* path_bytes;
    if (java_flags & com_jeffplaisance_mmap_NativeUtils_O_RDONLY) {
        flags |= O_RDONLY;
    }
    if (java_flags & com_jeffplaisance_mmap_NativeUtils_O_WRONLY) {
        flags |= O_WRONLY;
    }
    if (java_flags & com_jeffplaisance_mmap_NativeUtils_O_RDWR) {
        flags |= O_RDWR;
    }
    if (java_flags & com_jeffplaisance_mmap_NativeUtils_O_CREAT) {
        flags |= O_CREAT;
    }
    if (java_flags & com_jeffplaisance_mmap_NativeUtils_O_APPEND) {
        flags |= O_APPEND;
    }
    if (java_flags & com_jeffplaisance_mmap_NativeUtils_O_TRUNC) {
        flags |= O_TRUNC;
    }
    if (java_flags & com_jeffplaisance_mmap_NativeUtils_O_EXCL) {
        flags |= O_EXCL;
    }
    path_bytes = (*env)->GetStringUTFChars(env, path, NULL);
    fd = open(path_bytes, flags, mode);
    (*env)->ReleaseStringUTFChars(env, path, path_bytes);
    return fd;
}

JNIEXPORT jint JNICALL Java_com_jeffplaisance_mmap_NativeUtils_close0(JNIEnv* env, jclass class, jint fd) {
    return close(fd);
}

JNIEXPORT jint JNICALL Java_com_jeffplaisance_mmap_NativeUtils_ftruncate0(JNIEnv* env, jclass class, jint fd, jlong newLength) {
    return ftruncate(fd, newLength);
}

JNIEXPORT jint JNICALL Java_com_jeffplaisance_mmap_NativeUtils_errno(JNIEnv* env, jclass class) {
    int err = errno;
    switch (err) {
        case EACCES:
            return com_jeffplaisance_mmap_NativeUtils_EACCES;
        case EAGAIN:
            return com_jeffplaisance_mmap_NativeUtils_EAGAIN;
        case EBADF:
            return com_jeffplaisance_mmap_NativeUtils_EBADF;
        case EINVAL:
            return com_jeffplaisance_mmap_NativeUtils_EINVAL;
        case ENFILE:
            return com_jeffplaisance_mmap_NativeUtils_ENFILE;
        case ENODEV:
            return com_jeffplaisance_mmap_NativeUtils_ENODEV;
        case ENOMEM:
            return com_jeffplaisance_mmap_NativeUtils_ENOMEM;
        case EPERM:
            return com_jeffplaisance_mmap_NativeUtils_EPERM;
        case ETXTBSY:
            return com_jeffplaisance_mmap_NativeUtils_ETXTBSY;
        case EOVERFLOW:
            return com_jeffplaisance_mmap_NativeUtils_EOVERFLOW;
        case EINTR:
            return com_jeffplaisance_mmap_NativeUtils_EINTR;
        case EIO:
            return com_jeffplaisance_mmap_NativeUtils_EIO;
        case EEXIST:
            return com_jeffplaisance_mmap_NativeUtils_EEXIST;
        case EFBIG:
            return com_jeffplaisance_mmap_NativeUtils_EFBIG;
        case EISDIR:
            return com_jeffplaisance_mmap_NativeUtils_EISDIR;
        case EMFILE:
            return com_jeffplaisance_mmap_NativeUtils_EMFILE;
        case ENAMETOOLONG:
            return com_jeffplaisance_mmap_NativeUtils_ENAMETOOLONG;
        case EDQUOT:
            return com_jeffplaisance_mmap_NativeUtils_EDQUOT;
        case ELOOP:
            return com_jeffplaisance_mmap_NativeUtils_ELOOP;
        case ENOENT:
            return com_jeffplaisance_mmap_NativeUtils_ENOENT;
        case ENOSPC:
            return com_jeffplaisance_mmap_NativeUtils_ENOSPC;
        case ENOTDIR:
            return com_jeffplaisance_mmap_NativeUtils_ENOTDIR;
        case ENXIO:
            return com_jeffplaisance_mmap_NativeUtils_ENXIO;
        case EROFS:
            return com_jeffplaisance_mmap_NativeUtils_EROFS;
        default:
            return com_jeffplaisance_mmap_NativeUtils_MAP_FAILED;
    }
}

JNIEXPORT jlong JNICALL Java_com_jeffplaisance_mmap_NativeUtils_getDirectBufferAddress(JNIEnv* env, jclass class, jobject buffer) {
    return (jlong)((*env)->GetDirectBufferAddress(env, buffer));
}
