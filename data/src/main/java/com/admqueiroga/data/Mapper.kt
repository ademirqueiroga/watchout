package com.admqueiroga.data

interface Mapper<in I, out O> : (I) -> O

interface ListMapper<in I, out O> : (List<I>) -> List<O>