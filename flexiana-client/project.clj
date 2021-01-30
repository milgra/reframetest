(defproject flexiana "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.773"
                  :exclusions [com.google.javascript/closure-compiler-unshaded
                               org.clojure/google-closure-library
                               org.clojure/google-closure-library-third-party]]
                 [thheller/shadow-cljs "2.11.7"]
                 [day8.re-frame/http-fx "0.2.2"]
                 [cljs-ajax "0.7.5"]
                 [reagent "0.10.0"]
                 [re-frame "1.1.2"]]

  :plugins [[lein-shadow "0.3.1"]

            [lein-shell "0.5.0"]]

  :min-lein-version "2.9.0"

  :source-paths ["src/clj" "src/cljs"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]


  :shadow-cljs {:nrepl {:port 8777}

                :builds {:app {:target :browser
                               :output-dir "resources/public/js/compiled"
                               :asset-path "/js/compiled"
                               :modules {:app {:init-fn flexiana.core/init
                                               :preloads [devtools.preload]}}

                               :devtools {:http-root "resources/public"
                                          :http-port 8280}}}}

  :aliases {"watch"        ["with-profile" "dev" "do"
                            ["shadow" "watch" "app"]]

            "release"      ["with-profile" "prod" "do"
                            ["shadow" "release" "app"]]}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "1.0.2"]]
    :source-paths ["dev"]}

   :prod {}}

  :prep-tasks [])
