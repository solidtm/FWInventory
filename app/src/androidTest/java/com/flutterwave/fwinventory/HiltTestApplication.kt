package com.flutterwave.fwinventory

import android.app.Application
import dagger.hilt.android.testing.CustomTestApplication

@CustomTestApplication(InventoryApplication::class)
class HiltTestApplication : Application()
