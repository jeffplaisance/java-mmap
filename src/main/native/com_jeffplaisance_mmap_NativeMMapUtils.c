#include "com_jeffplaisance_mmap_NativeMMapUtils.h"
#include <sys/mman.h>
#include <errno.h>

JNIEXPORT jlong JNICALL Java_com_jeffplaisance_mmap_NativeMMapUtils_mmap0(JNIEnv* env, jclass class, jlong addr, jlong length, jint prot, jint flags, jint fd, jlong offset) {
    void* ret = mmap((void*)addr, length, prot, flags, fd, offset);
    if (ret == MAP_FAILED) {
        return com_jeffplaisance_mmap_NativeMMapUtils_MAP_FAILED;
    }
    return (jlong) ret;
}

JNIEXPORT jint JNICALL Java_com_jeffplaisance_mmap_NativeMMapUtils_munmap0(JNIEnv* env, jclass class, jlong addr, jlong length) {
    return munmap((void*)addr, length);
}

JNIEXPORT jint JNICALL Java_com_jeffplaisance_mmap_NativeMMapUtils_errno(JNIEnv* env, jclass class) {
    int err = errno;
    switch (err) {
        case EACCES:
            return com_jeffplaisance_mmap_NativeMMapUtils_EACCES;
        case EAGAIN:
            return com_jeffplaisance_mmap_NativeMMapUtils_EAGAIN;
        case EBADF:
            return com_jeffplaisance_mmap_NativeMMapUtils_EBADF;
        case EINVAL:
            return com_jeffplaisance_mmap_NativeMMapUtils_EINVAL;
        case ENFILE:
            return com_jeffplaisance_mmap_NativeMMapUtils_ENFILE;
        case ENODEV:
            return com_jeffplaisance_mmap_NativeMMapUtils_ENODEV;
        case ENOMEM:
            return com_jeffplaisance_mmap_NativeMMapUtils_ENOMEM;
        case EPERM:
            return com_jeffplaisance_mmap_NativeMMapUtils_EPERM;
        case ETXTBSY:
            return com_jeffplaisance_mmap_NativeMMapUtils_ETXTBSY;
        case EOVERFLOW:
            return com_jeffplaisance_mmap_NativeMMapUtils_EOVERFLOW;
        default:
            return com_jeffplaisance_mmap_NativeMMapUtils_MAP_FAILED;
    }
}

JNIEXPORT jlong JNICALL Java_com_jeffplaisance_mmap_NativeMMapUtils_getDirectBufferAddress(JNIEnv* env, jclass class, jobject buffer) {
    return (jlong)((*env)->GetDirectBufferAddress(env, buffer));
}
