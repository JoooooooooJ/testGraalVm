package br.com.gubee.gubeegraalvmspring.shared


data class Channel(var name: String? = null,
                   var concurrency: Int = 0,
                   var replicas: Int = 0,
                   var partitions: Int = 0)
