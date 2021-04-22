# Buy or Rent

Buy or Rent is an Android app that allows you to quickly see a list of properties.

## Architecture

The app uses a view model architecture.

Here is an overview of the directory structure:

```
/api/
  API handling logic

/domain/
  domain models (agnostic of API and views)

/repositories/
  domain repositories interfaces

  /remote/
    implementation of domain repositories

/ui/
  user interface logic
```

This structure helps enforce that the UI is only dependent on domain and repositories interfaces. For example, we could switch to a local `FakePropertyRepository` without changing any UI code. The only package that depends on `/api/` is `/repositories/remote`, so we could also switch to a different backend without disruption.

## UI logic

`MainActivity` uses a tabbed layout to show `PropertyListFragment`, which binds its views to `PropertyListViewModel`.

Under the hood, `PropertyListViewModel` fetches data from `PropertyRepository`. Its implementation `RemotePropertyRepository` uses a `PagingSource` to fetch pages from `BuyRentApi`.

We use a `PagingDataAdapter` to get pagination automatically in the `Recyclerview`.

## Libraries

The app is based on the following libraries:
- Hilt (Dagger) for dependency injection.
- Retrofit for HTTP requests.
- Moshi for JSON (de)serialisation.
- MockK for mocking.
- Robolectric for unit testing (when required).
- Espresso for instrumentation testing.

## Testing

Testing is mostly achieved through instrumented tests. There are some unit tests too, but I didn't quite have enough time to get full coverage.

## Improvements to be made

- We currently ignore non-property search results, which don't have the expected data.
- `PropertyListViewModel` currently holds the adapter, which is tightly coupled to view rendering logic. A better architecture would probably extract the adapter out.
- We rely on the Jetpack's [paging library](https://developer.android.com/topic/libraries/architecture/paging), which is currently in 3.0 beta version. Ideally, we would only use final versions in production.
- While pagination capabilities are implemented, the underlying API doesn't seem to offer pagination. It should hopefully be a simple addition.
- Pagination currently relies on page numbers. A more resilient approach would be using opaque server-driven cursor strings, to ensure a frequently mutating dataset doesn't result in pagination bugs.
- The loading and error states could be implemented better using a [`LoadStateAdapter`](https://developer.android.com/topic/libraries/architecture/paging/v3-paged-data).

## Lessons learned

I had quite a bit of fun making this! I hadn't written much Android code since early 2017, and I'm quite pleased with how much the Android APIs have matured since. I ended up spending about 14 hours overall (including a fair bit of learning time).

Here are a few random things I learned:
- Android Studio doesn't work well on an Apple M1 laptop. Thankfully [someone made a fork](https://github.com/dsa28s/android-studio-apple-m1) which works reasonably well, but it's still rather sluggish.
- `LiveData` and Kotlin coroutines make code much simpler! I'm glad we don't need `RxJava` anymore to make a good app nowadays.
- `PagingData` is a great idea. I can't wait to see these ideas spread around the community.
- `Dagger` has really matured with `Hilt`. The boilerplate is quite minimal, especially when it comes to auto-generated factories with `@AssistedInject`.
