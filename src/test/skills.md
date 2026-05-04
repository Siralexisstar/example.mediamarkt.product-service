---

# 🧪 SKILL.md — Unit Testing for Spring WebFlux (JUnit 5)

## 🎯 Goal

Define a **robust, scalable, and production-ready testing strategy** for reactive applications using:

* Java 17+
* Spring Boot (WebFlux)
* Project Reactor (`Mono` / `Flux`)
* JUnit 5
* Mockito
* Reactor Test (`StepVerifier`)

**Focus:**

* Fast feedback
* Deterministic tests
* Clean architecture alignment
* Behavior-driven testing

## 🧠 Core Principles

* Tests MUST be:
* Fast (<100ms per unit test ideally)
* Isolated (no external systems)
* Deterministic (no randomness, no timing issues)
* Test **behavior, not implementation**
* Mock  **all external dependencies** :
* DB
* HTTP clients
* Messaging systems (Pub/Sub, Kafka)
* Each test MUST:
* Run successfully
* Assert meaningful behavior
* Fail for the right reason

❌ **Never use in unit tests:**

* `@SpringBootTest`
* Real DB connections
* Real HTTP calls

## Core Principles

- Tests must be fast, isolated, deterministic
- Do NOT load full Spring context for unit tests
- Test behavior, not implementation
- Mock all external dependencies (DB, HTTP, Pub/Sub)
- After do the test, verify it running, and correct if anyone it's faling

Avoid:
@SpringBootTest

## 🏷️ Naming Convention

Format:

<pre class="overflow-visible! px-0!" data-start="1208" data-end="1258"><div class="relative w-full mt-4 mb-1"><div class=""><div class="relative"><div class="h-full min-h-0 min-w-0"><div class="h-full min-h-0 min-w-0"><div class="border border-token-border-light border-radius-3xl corner-superellipse/1.1 rounded-3xl"><div class="h-full w-full border-radius-3xl bg-token-bg-elevated-secondary corner-superellipse/1.1 overflow-clip rounded-3xl lxnfua_clipPathFallback"><div class="pointer-events-none absolute end-1.5 top-1 z-2 md:end-2 md:top-1"></div><div class="relative"><div class="pe-11 pt-3"><div class="relative z-0 flex max-w-full"><div id="code-block-viewer" dir="ltr" class="q9tKkq_viewer cm-editor z-10 light:cm-light dark:cm-light flex h-full w-full flex-col items-stretch ͼs ͼ16"><div class="cm-scroller"><pre class="cm-content q9tKkq_readonly m-0"><code><span>should_<expectedBehavior>_when_<condition></span></code></pre></div></div></div></div></div></div></div></div></div><div class=""><div class=""></div></div></div></div></div></pre>

Examples:

<pre class="overflow-visible! px-0!" data-start="1271" data-end="1421"><div class="relative w-full mt-4 mb-1"><div class=""><div class="relative"><div class="h-full min-h-0 min-w-0"><div class="h-full min-h-0 min-w-0"><div class="border border-token-border-light border-radius-3xl corner-superellipse/1.1 rounded-3xl"><div class="h-full w-full border-radius-3xl bg-token-bg-elevated-secondary corner-superellipse/1.1 overflow-clip rounded-3xl lxnfua_clipPathFallback"><div class="pointer-events-none absolute inset-x-4 top-12 bottom-4"><div class="pointer-events-none sticky z-40 shrink-0 z-1!"><div class="sticky bg-token-border-light"></div></div></div><div class="relative"><div class=""><div class="relative z-0 flex max-w-full"><div id="code-block-viewer" dir="ltr" class="q9tKkq_viewer cm-editor z-10 light:cm-light dark:cm-light flex h-full w-full flex-col items-stretch ͼs ͼ16"><div class="cm-scroller"><pre class="cm-content q9tKkq_readonly m-0"><code><span class="ͼ11">shouldReturnProduct_whenProductExists</span><span>()</span><br/><span class="ͼ11">shouldThrowNotFoundException_whenProductDoesNotExist</span><span>()</span><br/><span class="ͼ11">shouldReturnEmptyFlux_whenNoDataAvailable</span><span>()</span></code></pre></div></div></div></div></div></div></div></div></div><div class=""><div class=""></div></div></div></div></div></pre>

✔ Always use:

<pre class="overflow-visible! px-0!" data-start="1438" data-end="1507"><div class="relative w-full mt-4 mb-1"><div class=""><div class="relative"><div class="h-full min-h-0 min-w-0"><div class="h-full min-h-0 min-w-0"><div class="border border-token-border-light border-radius-3xl corner-superellipse/1.1 rounded-3xl"><div class="h-full w-full border-radius-3xl bg-token-bg-elevated-secondary corner-superellipse/1.1 overflow-clip rounded-3xl lxnfua_clipPathFallback"><div class="pointer-events-none absolute inset-x-4 top-12 bottom-4"><div class="pointer-events-none sticky z-40 shrink-0 z-1!"><div class="sticky bg-token-border-light"></div></div></div><div class="relative"><div class=""><div class="relative z-0 flex max-w-full"><div id="code-block-viewer" dir="ltr" class="q9tKkq_viewer cm-editor z-10 light:cm-light dark:cm-light flex h-full w-full flex-col items-stretch ͼs ͼ16"><div class="cm-scroller"><pre class="cm-content q9tKkq_readonly m-0"><code><span>@</span><span class="ͼ11">DisplayName</span><span>(</span><span class="ͼz">"Should return product when product exists"</span><span>)</span></code></pre></div></div></div></div></div></div></div></div></div><div class=""><div class=""></div></div></div></div></div></pre>

---

## 🧱 Test Structure (AAA)

All tests MUST follow:

<pre class="overflow-visible! px-0!" data-start="1566" data-end="1596"><div class="relative w-full mt-4 mb-1"><div class=""><div class="relative"><div class="h-full min-h-0 min-w-0"><div class="h-full min-h-0 min-w-0"><div class="border border-token-border-light border-radius-3xl corner-superellipse/1.1 rounded-3xl"><div class="h-full w-full border-radius-3xl bg-token-bg-elevated-secondary corner-superellipse/1.1 overflow-clip rounded-3xl lxnfua_clipPathFallback"><div class="pointer-events-none absolute end-1.5 top-1 z-2 md:end-2 md:top-1"></div><div class="relative"><div class="pe-11 pt-3"><div class="relative z-0 flex max-w-full"><div id="code-block-viewer" dir="ltr" class="q9tKkq_viewer cm-editor z-10 light:cm-light dark:cm-light flex h-full w-full flex-col items-stretch ͼs ͼ16"><div class="cm-scroller"><pre class="cm-content q9tKkq_readonly m-0"><code><span>Arrange → Act → Assert</span></code></pre></div></div></div></div></div></div></div></div></div><div class=""><div class=""></div></div></div></div></div></pre>

Example:

<pre class="overflow-visible! px-0!" data-start="1608" data-end="1828"><div class="relative w-full mt-4 mb-1"><div class=""><div class="relative"><div class="h-full min-h-0 min-w-0"><div class="h-full min-h-0 min-w-0"><div class="border border-token-border-light border-radius-3xl corner-superellipse/1.1 rounded-3xl"><div class="h-full w-full border-radius-3xl bg-token-bg-elevated-secondary corner-superellipse/1.1 overflow-clip rounded-3xl lxnfua_clipPathFallback"><div class="pointer-events-none absolute inset-x-4 top-12 bottom-4"><div class="pointer-events-none sticky z-40 shrink-0 z-1!"><div class="sticky bg-token-border-light"></div></div></div><div class="relative"><div class=""><div class="relative z-0 flex max-w-full"><div id="code-block-viewer" dir="ltr" class="q9tKkq_viewer cm-editor z-10 light:cm-light dark:cm-light flex h-full w-full flex-col items-stretch ͼs ͼ16"><div class="cm-scroller"><pre class="cm-content q9tKkq_readonly m-0"><code><span class="ͼt">// Arrange</span><br/><span class="ͼ11">when</span><span>(</span><span class="ͼ11">repo</span><span class="ͼv">.</span><span class="ͼ11">findById</span><span>(</span><span class="ͼz">"1"</span><span>))</span><span class="ͼv">.</span><span class="ͼ11">thenReturn</span><span>(</span><span class="ͼ11">Mono</span><span class="ͼv">.</span><span class="ͼ11">just</span><span>(</span><span class="ͼ11">product</span><span>));</span><br/><br/><span class="ͼt">// Act</span><br/><span class="ͼ11">Mono</span><span><</span><span class="ͼ11">Product</span><span>> </span><span class="ͼ11">result</span><span></span><span class="ͼv">=</span><span></span><span class="ͼ11">service</span><span class="ͼv">.</span><span class="ͼ11">findById</span><span>(</span><span class="ͼz">"1"</span><span>);</span><br/><br/><span class="ͼt">// Assert</span><br/><span class="ͼ11">StepVerifier</span><span class="ͼv">.</span><span class="ͼ11">create</span><span>(</span><span class="ͼ11">result</span><span>)</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">expectNext</span><span>(</span><span class="ͼ11">product</span><span>)</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">verifyComplete</span><span>();</span></code></pre></div></div></div></div></div></div></div></div></div><div class=""><div class=""></div></div></div></div></div></pre>

---

## ⚙️ Setup (Mockito + JUnit 5)

<pre class="overflow-visible! px-0!" data-start="1868" data-end="2034"><div class="relative w-full mt-4 mb-1"><div class=""><div class="relative"><div class="h-full min-h-0 min-w-0"><div class="h-full min-h-0 min-w-0"><div class="border border-token-border-light border-radius-3xl corner-superellipse/1.1 rounded-3xl"><div class="h-full w-full border-radius-3xl bg-token-bg-elevated-secondary corner-superellipse/1.1 overflow-clip rounded-3xl lxnfua_clipPathFallback"><div class="pointer-events-none absolute inset-x-4 top-12 bottom-4"><div class="pointer-events-none sticky z-40 shrink-0 z-1!"><div class="sticky bg-token-border-light"></div></div></div><div class="relative"><div class=""><div class="relative z-0 flex max-w-full"><div id="code-block-viewer" dir="ltr" class="q9tKkq_viewer cm-editor z-10 light:cm-light dark:cm-light flex h-full w-full flex-col items-stretch ͼs ͼ16"><div class="cm-scroller"><pre class="cm-content q9tKkq_readonly m-0"><code><span>@</span><span class="ͼ11">ExtendWith</span><span>(</span><span class="ͼ11">MockitoExtension</span><span class="ͼv">.class</span><span>)</span><br/><span class="ͼv">class</span><span></span><span class="ͼ11">MyServiceTest</span><span> {</span><br/><br/><span>    @</span><span class="ͼ11">Mock</span><br/><span></span><span class="ͼv">private</span><span></span><span class="ͼ11">Repository</span><span></span><span class="ͼ11">repository</span><span>;</span><br/><br/><span>    @</span><span class="ͼ11">InjectMocks</span><br/><span></span><span class="ͼv">private</span><span></span><span class="ͼ11">MyService</span><span></span><span class="ͼ11">service</span><span>;</span><br/><span>}</span></code></pre></div></div></div></div></div></div></div></div></div><div class=""><div class=""></div></div></div></div></div></pre>

✔ Prefer constructor injection in production code → easier to test

---

## 🔄 Reactive Testing Rules

### 🚫 NEVER

* `.block()`
* `.subscribe()`
* `Thread.sleep()`

### ✅ ALWAYS

* Use `StepVerifier`
* Test all reactive flows:
* success
* empty
* error

---

## 🔹 Testing `Mono`

<pre class="overflow-visible! px-0!" data-start="2325" data-end="2413"><div class="relative w-full mt-4 mb-1"><div class=""><div class="relative"><div class="h-full min-h-0 min-w-0"><div class="h-full min-h-0 min-w-0"><div class="border border-token-border-light border-radius-3xl corner-superellipse/1.1 rounded-3xl"><div class="h-full w-full border-radius-3xl bg-token-bg-elevated-secondary corner-superellipse/1.1 overflow-clip rounded-3xl lxnfua_clipPathFallback"><div class="pointer-events-none absolute inset-x-4 top-12 bottom-4"><div class="pointer-events-none sticky z-40 shrink-0 z-1!"><div class="sticky bg-token-border-light"></div></div></div><div class="relative"><div class=""><div class="relative z-0 flex max-w-full"><div id="code-block-viewer" dir="ltr" class="q9tKkq_viewer cm-editor z-10 light:cm-light dark:cm-light flex h-full w-full flex-col items-stretch ͼs ͼ16"><div class="cm-scroller"><pre class="cm-content q9tKkq_readonly m-0"><code><span class="ͼ11">StepVerifier</span><span class="ͼv">.</span><span class="ͼ11">create</span><span>(</span><span class="ͼ11">result</span><span>)</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">expectNext</span><span>(</span><span class="ͼ11">expected</span><span>)</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">verifyComplete</span><span>();</span></code></pre></div></div></div></div></div></div></div></div></div><div class=""><div class=""></div></div></div></div></div></pre>

With assertions:

<pre class="overflow-visible! px-0!" data-start="2433" data-end="2571"><div class="relative w-full mt-4 mb-1"><div class=""><div class="relative"><div class="h-full min-h-0 min-w-0"><div class="h-full min-h-0 min-w-0"><div class="border border-token-border-light border-radius-3xl corner-superellipse/1.1 rounded-3xl"><div class="h-full w-full border-radius-3xl bg-token-bg-elevated-secondary corner-superellipse/1.1 overflow-clip rounded-3xl lxnfua_clipPathFallback"><div class="pointer-events-none absolute inset-x-4 top-12 bottom-4"><div class="pointer-events-none sticky z-40 shrink-0 z-1!"><div class="sticky bg-token-border-light"></div></div></div><div class="relative"><div class=""><div class="relative z-0 flex max-w-full"><div id="code-block-viewer" dir="ltr" class="q9tKkq_viewer cm-editor z-10 light:cm-light dark:cm-light flex h-full w-full flex-col items-stretch ͼs ͼ16"><div class="cm-scroller"><pre class="cm-content q9tKkq_readonly m-0"><code><span class="ͼ11">StepVerifier</span><span class="ͼv">.</span><span class="ͼ11">create</span><span>(</span><span class="ͼ11">result</span><span>)</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">assertNext</span><span>(</span><span class="ͼ11">r</span><span> -> {</span><br/><span></span><span class="ͼ11">assertThat</span><span>(</span><span class="ͼ11">r</span><span class="ͼv">.</span><span class="ͼ11">getId</span><span>())</span><span class="ͼv">.</span><span class="ͼ11">isEqualTo</span><span>(</span><span class="ͼz">"1"</span><span>);</span><br/><span>    })</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">verifyComplete</span><span>();</span></code></pre></div></div></div></div></div></div></div></div></div><div class=""><div class=""></div></div></div></div></div></pre>

---

## 🔹 Testing `Flux`

<pre class="overflow-visible! px-0!" data-start="2600" data-end="2692"><div class="relative w-full mt-4 mb-1"><div class=""><div class="relative"><div class="h-full min-h-0 min-w-0"><div class="h-full min-h-0 min-w-0"><div class="border border-token-border-light border-radius-3xl corner-superellipse/1.1 rounded-3xl"><div class="h-full w-full border-radius-3xl bg-token-bg-elevated-secondary corner-superellipse/1.1 overflow-clip rounded-3xl lxnfua_clipPathFallback"><div class="pointer-events-none absolute inset-x-4 top-12 bottom-4"><div class="pointer-events-none sticky z-40 shrink-0 z-1!"><div class="sticky bg-token-border-light"></div></div></div><div class="relative"><div class=""><div class="relative z-0 flex max-w-full"><div id="code-block-viewer" dir="ltr" class="q9tKkq_viewer cm-editor z-10 light:cm-light dark:cm-light flex h-full w-full flex-col items-stretch ͼs ͼ16"><div class="cm-scroller"><pre class="cm-content q9tKkq_readonly m-0"><code><span class="ͼ11">StepVerifier</span><span class="ͼv">.</span><span class="ͼ11">create</span><span>(</span><span class="ͼ11">result</span><span>)</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">expectNext</span><span>(</span><span class="ͼ11">item1</span><span>, </span><span class="ͼ11">item2</span><span>)</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">verifyComplete</span><span>();</span></code></pre></div></div></div></div></div></div></div></div></div><div class=""><div class=""></div></div></div></div></div></pre>

For collections:

<pre class="overflow-visible! px-0!" data-start="2712" data-end="2798"><div class="relative w-full mt-4 mb-1"><div class=""><div class="relative"><div class="h-full min-h-0 min-w-0"><div class="h-full min-h-0 min-w-0"><div class="border border-token-border-light border-radius-3xl corner-superellipse/1.1 rounded-3xl"><div class="h-full w-full border-radius-3xl bg-token-bg-elevated-secondary corner-superellipse/1.1 overflow-clip rounded-3xl lxnfua_clipPathFallback"><div class="pointer-events-none absolute inset-x-4 top-12 bottom-4"><div class="pointer-events-none sticky z-40 shrink-0 z-1!"><div class="sticky bg-token-border-light"></div></div></div><div class="relative"><div class=""><div class="relative z-0 flex max-w-full"><div id="code-block-viewer" dir="ltr" class="q9tKkq_viewer cm-editor z-10 light:cm-light dark:cm-light flex h-full w-full flex-col items-stretch ͼs ͼ16"><div class="cm-scroller"><pre class="cm-content q9tKkq_readonly m-0"><code><span class="ͼ11">StepVerifier</span><span class="ͼv">.</span><span class="ͼ11">create</span><span>(</span><span class="ͼ11">result</span><span>)</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">expectNextCount</span><span>(</span><span class="ͼy">3</span><span>)</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">verifyComplete</span><span>();</span></code></pre></div></div></div></div></div></div></div></div></div><div class=""><div class=""></div></div></div></div></div></pre>

---

## 🔥 Testing Errors

<pre class="overflow-visible! px-0!" data-start="2827" data-end="2917"><div class="relative w-full mt-4 mb-1"><div class=""><div class="relative"><div class="h-full min-h-0 min-w-0"><div class="h-full min-h-0 min-w-0"><div class="border border-token-border-light border-radius-3xl corner-superellipse/1.1 rounded-3xl"><div class="h-full w-full border-radius-3xl bg-token-bg-elevated-secondary corner-superellipse/1.1 overflow-clip rounded-3xl lxnfua_clipPathFallback"><div class="pointer-events-none absolute inset-x-4 top-12 bottom-4"><div class="pointer-events-none sticky z-40 shrink-0 z-1!"><div class="sticky bg-token-border-light"></div></div></div><div class="relative"><div class=""><div class="relative z-0 flex max-w-full"><div id="code-block-viewer" dir="ltr" class="q9tKkq_viewer cm-editor z-10 light:cm-light dark:cm-light flex h-full w-full flex-col items-stretch ͼs ͼ16"><div class="cm-scroller"><pre class="cm-content q9tKkq_readonly m-0"><code><span class="ͼ11">StepVerifier</span><span class="ͼv">.</span><span class="ͼ11">create</span><span>(</span><span class="ͼ11">result</span><span>)</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">expectError</span><span>(</span><span class="ͼ11">MyException</span><span class="ͼv">.class</span><span>)</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">verify</span><span>();</span></code></pre></div></div></div></div></div></div></div></div></div><div class=""><div class=""></div></div></div></div></div></pre>

More strict:

<pre class="overflow-visible! px-0!" data-start="2933" data-end="3064"><div class="relative w-full mt-4 mb-1"><div class=""><div class="relative"><div class="h-full min-h-0 min-w-0"><div class="h-full min-h-0 min-w-0"><div class="border border-token-border-light border-radius-3xl corner-superellipse/1.1 rounded-3xl"><div class="h-full w-full border-radius-3xl bg-token-bg-elevated-secondary corner-superellipse/1.1 overflow-clip rounded-3xl lxnfua_clipPathFallback"><div class="pointer-events-none absolute inset-x-4 top-12 bottom-4"><div class="pointer-events-none sticky z-40 shrink-0 z-1!"><div class="sticky bg-token-border-light"></div></div></div><div class="relative"><div class=""><div class="relative z-0 flex max-w-full"><div id="code-block-viewer" dir="ltr" class="q9tKkq_viewer cm-editor z-10 light:cm-light dark:cm-light flex h-full w-full flex-col items-stretch ͼs ͼ16"><div class="cm-scroller"><pre class="cm-content q9tKkq_readonly m-0"><code><span>.</span><span class="ͼ11">expectErrorMatches</span><span>(</span><span class="ͼ11">ex</span><span> -> </span><span class="ͼ11">ex</span><span></span><span class="ͼv">instanceof</span><span></span><span class="ͼ11">MyException</span><span></span><span class="ͼv">&&</span><br/><span></span><span class="ͼ11">ex</span><span class="ͼv">.</span><span class="ͼ11">getMessage</span><span>()</span><span class="ͼv">.</span><span class="ͼ11">contains</span><span>(</span><span class="ͼz">"not found"</span><span>))</span></code></pre></div></div></div></div></div></div></div></div></div><div class=""><div class=""></div></div></div></div></div></pre>

---

## ⚪ Testing Empty

<pre class="overflow-visible! px-0!" data-start="3091" data-end="3153"><div class="relative w-full mt-4 mb-1"><div class=""><div class="relative"><div class="h-full min-h-0 min-w-0"><div class="h-full min-h-0 min-w-0"><div class="border border-token-border-light border-radius-3xl corner-superellipse/1.1 rounded-3xl"><div class="h-full w-full border-radius-3xl bg-token-bg-elevated-secondary corner-superellipse/1.1 overflow-clip rounded-3xl lxnfua_clipPathFallback"><div class="pointer-events-none absolute inset-x-4 top-12 bottom-4"><div class="pointer-events-none sticky z-40 shrink-0 z-1!"><div class="sticky bg-token-border-light"></div></div></div><div class="relative"><div class=""><div class="relative z-0 flex max-w-full"><div id="code-block-viewer" dir="ltr" class="q9tKkq_viewer cm-editor z-10 light:cm-light dark:cm-light flex h-full w-full flex-col items-stretch ͼs ͼ16"><div class="cm-scroller"><pre class="cm-content q9tKkq_readonly m-0"><code><span class="ͼ11">StepVerifier</span><span class="ͼv">.</span><span class="ͼ11">create</span><span>(</span><span class="ͼ11">result</span><span>)</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">verifyComplete</span><span>();</span></code></pre></div></div></div></div></div></div></div></div></div><div class=""><div class=""></div></div></div></div></div></pre>

---

## 🔁 Testing Special Operators

### `switchIfEmpty`

<pre class="overflow-visible! px-0!" data-start="3214" data-end="3362"><div class="relative w-full mt-4 mb-1"><div class=""><div class="relative"><div class="h-full min-h-0 min-w-0"><div class="h-full min-h-0 min-w-0"><div class="border border-token-border-light border-radius-3xl corner-superellipse/1.1 rounded-3xl"><div class="h-full w-full border-radius-3xl bg-token-bg-elevated-secondary corner-superellipse/1.1 overflow-clip rounded-3xl lxnfua_clipPathFallback"><div class="pointer-events-none absolute inset-x-4 top-12 bottom-4"><div class="pointer-events-none sticky z-40 shrink-0 z-1!"><div class="sticky bg-token-border-light"></div></div></div><div class="relative"><div class=""><div class="relative z-0 flex max-w-full"><div id="code-block-viewer" dir="ltr" class="q9tKkq_viewer cm-editor z-10 light:cm-light dark:cm-light flex h-full w-full flex-col items-stretch ͼs ͼ16"><div class="cm-scroller"><pre class="cm-content q9tKkq_readonly m-0"><code><span class="ͼ11">when</span><span>(</span><span class="ͼ11">repo</span><span class="ͼv">.</span><span class="ͼ11">findById</span><span>(</span><span class="ͼz">"1"</span><span>))</span><span class="ͼv">.</span><span class="ͼ11">thenReturn</span><span>(</span><span class="ͼ11">Mono</span><span class="ͼv">.</span><span class="ͼ11">empty</span><span>());</span><br/><br/><span class="ͼ11">StepVerifier</span><span class="ͼv">.</span><span class="ͼ11">create</span><span>(</span><span class="ͼ11">result</span><span>)</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">expectError</span><span>(</span><span class="ͼ11">NotFoundException</span><span class="ͼv">.class</span><span>)</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">verify</span><span>();</span></code></pre></div></div></div></div></div></div></div></div></div><div class=""><div class=""></div></div></div></div></div></pre>

---

### `onErrorResume`

<pre class="overflow-visible! px-0!" data-start="3390" data-end="3557"><div class="relative w-full mt-4 mb-1"><div class=""><div class="relative"><div class="h-full min-h-0 min-w-0"><div class="h-full min-h-0 min-w-0"><div class="border border-token-border-light border-radius-3xl corner-superellipse/1.1 rounded-3xl"><div class="h-full w-full border-radius-3xl bg-token-bg-elevated-secondary corner-superellipse/1.1 overflow-clip rounded-3xl lxnfua_clipPathFallback"><div class="pointer-events-none absolute inset-x-4 top-12 bottom-4"><div class="pointer-events-none sticky z-40 shrink-0 z-1!"><div class="sticky bg-token-border-light"></div></div></div><div class="relative"><div class=""><div class="relative z-0 flex max-w-full"><div id="code-block-viewer" dir="ltr" class="q9tKkq_viewer cm-editor z-10 light:cm-light dark:cm-light flex h-full w-full flex-col items-stretch ͼs ͼ16"><div class="cm-scroller"><pre class="cm-content q9tKkq_readonly m-0"><code><span class="ͼ11">when</span><span>(</span><span class="ͼ11">repo</span><span class="ͼv">.</span><span class="ͼ11">findById</span><span>(</span><span class="ͼz">"1"</span><span>))</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">thenReturn</span><span>(</span><span class="ͼ11">Mono</span><span class="ͼv">.</span><span class="ͼ11">error</span><span>(</span><span class="ͼv">new</span><span></span><span class="ͼ11">RuntimeException</span><span>()));</span><br/><br/><span class="ͼ11">StepVerifier</span><span class="ͼv">.</span><span class="ͼ11">create</span><span>(</span><span class="ͼ11">result</span><span>)</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">expectNext</span><span>(</span><span class="ͼ11">fallback</span><span>)</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">verifyComplete</span><span>();</span></code></pre></div></div></div></div></div></div></div></div></div><div class=""><div class=""></div></div></div></div></div></pre>

---

### `then()` (Mono`<Void>`)

<pre class="overflow-visible! px-0!" data-start="3591" data-end="3653"><div class="relative w-full mt-4 mb-1"><div class=""><div class="relative"><div class="h-full min-h-0 min-w-0"><div class="h-full min-h-0 min-w-0"><div class="border border-token-border-light border-radius-3xl corner-superellipse/1.1 rounded-3xl"><div class="h-full w-full border-radius-3xl bg-token-bg-elevated-secondary corner-superellipse/1.1 overflow-clip rounded-3xl lxnfua_clipPathFallback"><div class="pointer-events-none absolute inset-x-4 top-12 bottom-4"><div class="pointer-events-none sticky z-40 shrink-0 z-1!"><div class="sticky bg-token-border-light"></div></div></div><div class="relative"><div class=""><div class="relative z-0 flex max-w-full"><div id="code-block-viewer" dir="ltr" class="q9tKkq_viewer cm-editor z-10 light:cm-light dark:cm-light flex h-full w-full flex-col items-stretch ͼs ͼ16"><div class="cm-scroller"><pre class="cm-content q9tKkq_readonly m-0"><code><span class="ͼ11">StepVerifier</span><span class="ͼv">.</span><span class="ͼ11">create</span><span>(</span><span class="ͼ11">result</span><span>)</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">verifyComplete</span><span>();</span></code></pre></div></div></div></div></div></div></div></div></div><div class=""><div class=""></div></div></div></div></div></pre>

---

## 🌐 Controller Testing (WebFlux)

Use  **slice testing** , not full context.

<pre class="overflow-visible! px-0!" data-start="3738" data-end="3910"><div class="relative w-full mt-4 mb-1"><div class=""><div class="relative"><div class="h-full min-h-0 min-w-0"><div class="h-full min-h-0 min-w-0"><div class="border border-token-border-light border-radius-3xl corner-superellipse/1.1 rounded-3xl"><div class="h-full w-full border-radius-3xl bg-token-bg-elevated-secondary corner-superellipse/1.1 overflow-clip rounded-3xl lxnfua_clipPathFallback"><div class="pointer-events-none absolute inset-x-4 top-12 bottom-4"><div class="pointer-events-none sticky z-40 shrink-0 z-1!"><div class="sticky bg-token-border-light"></div></div></div><div class="relative"><div class=""><div class="relative z-0 flex max-w-full"><div id="code-block-viewer" dir="ltr" class="q9tKkq_viewer cm-editor z-10 light:cm-light dark:cm-light flex h-full w-full flex-col items-stretch ͼs ͼ16"><div class="cm-scroller"><pre class="cm-content q9tKkq_readonly m-0"><code><span>@</span><span class="ͼ11">WebFluxTest</span><span>(</span><span class="ͼ11">MyController</span><span class="ͼv">.class</span><span>)</span><br/><span class="ͼv">class</span><span></span><span class="ͼ11">MyControllerTest</span><span> {</span><br/><br/><span>    @</span><span class="ͼ11">Autowired</span><br/><span></span><span class="ͼv">private</span><span></span><span class="ͼ11">WebTestClient</span><span></span><span class="ͼ11">webTestClient</span><span>;</span><br/><br/><span>    @</span><span class="ͼ11">MockBean</span><br/><span></span><span class="ͼv">private</span><span></span><span class="ͼ11">UseCase</span><span></span><span class="ͼ11">useCase</span><span>;</span><br/><span>}</span></code></pre></div></div></div></div></div></div></div></div></div><div class=""><div class=""></div></div></div></div></div></pre>

Example:

<pre class="overflow-visible! px-0!" data-start="3922" data-end="4076"><div class="relative w-full mt-4 mb-1"><div class=""><div class="relative"><div class="h-full min-h-0 min-w-0"><div class="h-full min-h-0 min-w-0"><div class="border border-token-border-light border-radius-3xl corner-superellipse/1.1 rounded-3xl"><div class="h-full w-full border-radius-3xl bg-token-bg-elevated-secondary corner-superellipse/1.1 overflow-clip rounded-3xl lxnfua_clipPathFallback"><div class="pointer-events-none absolute inset-x-4 top-12 bottom-4"><div class="pointer-events-none sticky z-40 shrink-0 z-1!"><div class="sticky bg-token-border-light"></div></div></div><div class="relative"><div class=""><div class="relative z-0 flex max-w-full"><div id="code-block-viewer" dir="ltr" class="q9tKkq_viewer cm-editor z-10 light:cm-light dark:cm-light flex h-full w-full flex-col items-stretch ͼs ͼ16"><div class="cm-scroller"><pre class="cm-content q9tKkq_readonly m-0"><code><span class="ͼ11">webTestClient</span><span class="ͼv">.</span><span class="ͼ11">get</span><span>()</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">uri</span><span>(</span><span class="ͼz">"/products/1"</span><span>)</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">exchange</span><span>()</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">expectStatus</span><span>()</span><span class="ͼv">.</span><span class="ͼ11">isOk</span><span>()</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">expectBody</span><span>()</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">jsonPath</span><span>(</span><span class="ͼz">"$.id"</span><span>)</span><span class="ͼv">.</span><span class="ͼ11">isEqualTo</span><span>(</span><span class="ͼz">"1"</span><span>);</span></code></pre></div></div></div></div></div></div></div></div></div><div class=""><div class=""></div></div></div></div></div></pre>

✔ What to test:

* HTTP status
* Response body
* Validation errors

❌ What NOT to test:

* Business logic (belongs to service tests)

---

## 🔍 Mockito Verification

<pre class="overflow-visible! px-0!" data-start="4243" data-end="4326"><div class="relative w-full mt-4 mb-1"><div class=""><div class="relative"><div class="h-full min-h-0 min-w-0"><div class="h-full min-h-0 min-w-0"><div class="border border-token-border-light border-radius-3xl corner-superellipse/1.1 rounded-3xl"><div class="h-full w-full border-radius-3xl bg-token-bg-elevated-secondary corner-superellipse/1.1 overflow-clip rounded-3xl lxnfua_clipPathFallback"><div class="pointer-events-none absolute inset-x-4 top-12 bottom-4"><div class="pointer-events-none sticky z-40 shrink-0 z-1!"><div class="sticky bg-token-border-light"></div></div></div><div class="relative"><div class=""><div class="relative z-0 flex max-w-full"><div id="code-block-viewer" dir="ltr" class="q9tKkq_viewer cm-editor z-10 light:cm-light dark:cm-light flex h-full w-full flex-col items-stretch ͼs ͼ16"><div class="cm-scroller"><pre class="cm-content q9tKkq_readonly m-0"><code><span class="ͼ11">verify</span><span>(</span><span class="ͼ11">repository</span><span>)</span><span class="ͼv">.</span><span class="ͼ11">findById</span><span>(</span><span class="ͼz">"1"</span><span>);</span><br/><span class="ͼ11">verifyNoMoreInteractions</span><span>(</span><span class="ͼ11">repository</span><span>);</span></code></pre></div></div></div></div></div></div></div></div></div><div class=""><div class=""></div></div></div></div></div></pre>

✔ Use verification ONLY when interaction matters (avoid over-testing)

---

## 🧩 Layer Testing Strategy (Clean Architecture)

### Domain

* Pure business rules
* No mocks needed

### Application (Use Cases)

* Main flows
* Error handling
* Port interactions (mocked)

### Infrastructure

* Adapters tested with mocks or integration tests

### Controllers

* HTTP layer only

---

## 🚫 Anti-Patterns

Avoid:

* Using `@SpringBootTest` for unit tests
* Calling `.block()` in tests
* Over-mocking everything
* Testing private methods
* Asserting internal state instead of behavior
* Using `Thread.sleep()`
* Ignoring error scenarios

---

## 📊 Coverage Guidelines

* Domain: **80%+**
* Use Cases: **85%+**
* Services: **85%+**
* Controllers: **70%+**

⚠️ Coverage is a  **signal** , not a goal.

Prioritize  **critical business paths** .

---

## 🧪 Full Example

<pre class="overflow-visible! px-0!" data-start="5185" data-end="5644"><div class="relative w-full mt-4 mb-1"><div class=""><div class="relative"><div class="h-full min-h-0 min-w-0"><div class="h-full min-h-0 min-w-0"><div class="border border-token-border-light border-radius-3xl corner-superellipse/1.1 rounded-3xl"><div class="h-full w-full border-radius-3xl bg-token-bg-elevated-secondary corner-superellipse/1.1 overflow-clip rounded-3xl lxnfua_clipPathFallback"><div class="pointer-events-none absolute inset-x-4 top-12 bottom-4"><div class="pointer-events-none sticky z-40 shrink-0 z-1!"><div class="sticky bg-token-border-light"></div></div></div><div class="relative"><div class=""><div class="relative z-0 flex max-w-full"><div id="code-block-viewer" dir="ltr" class="q9tKkq_viewer cm-editor z-10 light:cm-light dark:cm-light flex h-full w-full flex-col items-stretch ͼs ͼ16"><div class="cm-scroller"><pre class="cm-content q9tKkq_readonly m-0"><code><span>@</span><span class="ͼ11">Test</span><br/><span>@</span><span class="ͼ11">DisplayName</span><span>(</span><span class="ͼz">"Should return product when product exists"</span><span>)</span><br/><span class="ͼv">void</span><span></span><span class="ͼ11">shouldReturnProduct_whenProductExists</span><span>() {</span><br/><br/><span></span><span class="ͼt">// Arrange</span><br/><span></span><span class="ͼ11">Product</span><span></span><span class="ͼ11">product</span><span></span><span class="ͼv">=</span><span></span><span class="ͼv">new</span><span></span><span class="ͼ11">Product</span><span>(</span><span class="ͼz">"1"</span><span>, </span><span class="ͼz">"Laptop"</span><span>);</span><br/><br/><span></span><span class="ͼ11">when</span><span>(</span><span class="ͼ11">repo</span><span class="ͼv">.</span><span class="ͼ11">findById</span><span>(</span><span class="ͼz">"1"</span><span>))</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">thenReturn</span><span>(</span><span class="ͼ11">Mono</span><span class="ͼv">.</span><span class="ͼ11">just</span><span>(</span><span class="ͼ11">product</span><span>));</span><br/><br/><span></span><span class="ͼt">// Act</span><br/><span></span><span class="ͼ11">Mono</span><span><</span><span class="ͼ11">Product</span><span>> </span><span class="ͼ11">result</span><span></span><span class="ͼv">=</span><span></span><span class="ͼ11">service</span><span class="ͼv">.</span><span class="ͼ11">findById</span><span>(</span><span class="ͼz">"1"</span><span>);</span><br/><br/><span></span><span class="ͼt">// Assert</span><br/><span></span><span class="ͼ11">StepVerifier</span><span class="ͼv">.</span><span class="ͼ11">create</span><span>(</span><span class="ͼ11">result</span><span>)</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">expectNext</span><span>(</span><span class="ͼ11">product</span><span>)</span><br/><span></span><span class="ͼv">.</span><span class="ͼ11">verifyComplete</span><span>();</span><br/><br/><span></span><span class="ͼ11">verify</span><span>(</span><span class="ͼ11">repo</span><span>)</span><span class="ͼv">.</span><span class="ͼ11">findById</span><span>(</span><span class="ͼz">"1"</span><span>);</span><br/><span>}</span></code></pre></div></div></div></div></div></div></div></div></div><div class=""><div class=""></div></div></div></div></div></pre>

---

## 🏁 Final Rule

> A good test validates  **what the system does** ,
>
> not  **how it is implemented** .
>
