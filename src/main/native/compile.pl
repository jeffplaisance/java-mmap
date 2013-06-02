#!/usr/bin/perl
$java_home=$ENV{'JAVA_HOME'};
if (!$java_home) {
    $java_home = "/Library/Java/JavaVirtualMachines/jdk1.7.0_11.jdk/Contents/Home";
}
$jni="$java_home/include";
$gcc_includes = "-I$jni";
if (-d "$jni/darwin") {
    $gcc_includes = "$gcc_includes -I$jni/darwin";
}
$cmd = "gcc $gcc_includes -c -Wall -Wextra -fPIC com_jeffplaisance_mmap_NativeUtils.c";
print "$cmd\n";
`$cmd`;

$cmd = "gcc -dynamiclib -current_version 1.0 com_jeffplaisance_mmap_NativeUtils.o -o libjavammap.dylib";
print "$cmd\n";
`$cmd`;
