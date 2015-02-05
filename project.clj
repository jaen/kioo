(defproject org.clojars.jaen/kioo "0.4.1-SNAPSHOT"
  :description "enlive/enfocus style templating for Facebook's React."
  :url "http://github.com/jaen/kioo"
  :author "Creighton Kirkendall"
  :signing {:gpg-key "jaennirin@gmail.com"}
  :deploy-repositories [["clojars" {:creds :gpg}]]
  :min-lein-version "2.0.0"
  :lein-release {:deploy-via :clojars}
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[enlive "1.1.5"]
                 ; [com.facebook/react "0.11.1"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2760"]
                 [com.googlecode.htmlcompressor/htmlcompressor "1.5.2"]
                 [sablono "0.2.20"]
                 [hickory "0.5.3"]
                 [om "0.7.1"]
                 [reagent "0.4.2"]
                 [enlive-ws  "0.1.1"]]
  :auto-clean false
  :plugins [[lein-cljsbuild "1.0.4"]]
  :prep-tasks [["cljx" "once"]]
  :resource-paths ["src/cljs" "target/generated/src/cljs" "test-resources"] ; tmp
  :source-paths ["src/clj" "target/generated/src/clj"]
  :test-paths ["test" "target/generated/test"]
  :hooks [leiningen.cljsbuild]
  :profiles {:dev {:dependencies [[com.keminglabs/cljx "0.5.0" :exclusions [org.clojure/clojure]]]
                   :plugins [[com.keminglabs/cljx "0.5.0" :exclusions [org.clojure/clojure]] ;; Must be before Austin: https://github.com/cemerick/austin/issues/37
                             [com.cemerick/austin "0.1.7-SNAPSHOT"]
                             [com.cemerick/clojurescript.test "0.3.2-SNAPSHOT"]
                             [lein-cljsbuild "1.0.4"]
                             [lein-ancient "0.5.4"]]
                   :resource-paths ["test-resources"]
                   :cljx {:builds [{:source-paths ["src/cljx"]
                                    :output-path "target/generated/src/clj"
                                    :rules :clj}
                                   {:source-paths ["src/cljx"]
                                    :output-path "target/generated/src/cljs"
                                    :rules :cljs}
                                   {:source-paths ["src/cljx"]
                                    :output-path "target/generated/test/clj"
                                    :rules :clj}
                                   {:source-paths ["src/cljx"]
                                    :output-path "target/generated/test/cljs"
                                    :rules :cljs}]}
                   :cljsbuild {:builds [{:source-paths ["src" "test" "target/generated/src/cljs" "target/generated/test/cljs"]
                                         :compiler {:output-to "target/test/kioo.js"
                                                    :optimizations :simple
                                                    :pretty-print true
                                                    :preamble ["phantomjs-shims.js"
                                                               "react/react.js"]
                                                    :externs ["react/externs/react.js"]}}]
                               :test-commands {"phantom" ["phantomjs" :runner "target/test/kioo.js"]}}
                   :repl-options {:nrepl-middleware [cljx.repl-middleware/wrap-cljx]}
                   :injections [(require 'cemerick.austin.repls)
                                (defn browser-repl []
                                      (cemerick.austin.repls/cljs-repl (reset! cemerick.austin.repls/browser-repl-env
                                                                               (cemerick.austin/repl-env))))]}})
